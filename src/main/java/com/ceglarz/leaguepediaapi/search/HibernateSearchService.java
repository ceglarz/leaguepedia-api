package com.ceglarz.leaguepediaapi.search;

import com.ceglarz.leaguepediaapi.champion.Champion;
import com.ceglarz.leaguepediaapi.champion.ChampionDto;
import com.ceglarz.leaguepediaapi.faction.Faction;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HibernateSearchService {

    private final EntityManager entityManager;

    @Transactional
    public void rebuildIndex() {
        SearchSession searchSession = Search.session(entityManager);

        try {
            searchSession.massIndexer(Champion.class).threadsToLoadObjects(4).startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Rebuild index finished");
    }

    @Transactional
    public List<ChampionDto> findChampionsWhatever(String query) {
        SearchSession searchSession = Search.session(entityManager);
        List<Champion> hits = searchSession.search(Champion.class)
                .where(c -> c.match().fields("name", "title", "biographyFull", "biographyShort", "titlePl", "biographyFullPl", "biographyShortPl", "faction.overview", "faction.name").matching(query).fuzzy())
                .fetchAll().hits();

        return hits.stream().map(this::toDto).toList();
    }

    @Transactional
    public List<ChampionDto> findChampionsNameFirst(String query) {
        SearchSession searchSession = Search.session(entityManager);

        List<Champion> hits = searchSession.search(Champion.class)
                .where(c -> c.bool()
                        // must = AND
                        // should = OR
                        .should(c.match().field("name").matching(query).fuzzy().constantScore().boost(100))
                        .must(c.match().fields("name", "title", "biographyFull", "biographyShort", "titlePl", "biographyFullPl", "biographyShortPl").matching(query).fuzzy().constantScore().boost(1)))
                .sort(s -> s.score().desc()).fetchAll().hits();

        return hits.stream().map(this::toDto).toList();
    }

    @Transactional
    public List<ChampionDto> findChampionsFilterFaction(String query, String faction) {
        SearchSession searchSession = Search.session(entityManager);

        List<Champion> hits = searchSession.search(Champion.class).where(c -> c.bool(b -> {
            b.must(c.match().fields("name", "title", "biographyFull", "biographyShort", "titlePl", "biographyFullPl", "biographyShortPl").matching(query).fuzzy());
            if (Objects.nonNull(faction)) {
                b.must(c.match().fields("faction.name").matching(faction).fuzzy());
            }
        })).fetchAll().hits();

        return hits.stream().map(this::toDto).toList();
    }

    @Transactional
    public List<ChampionDto> findChampionsComplex(String query) {

        SearchSession searchSession = Search.session(entityManager);

        List<Champion> hits = searchSession.search(Champion.class)
                .where(c -> c.bool()
                        .should(c.wildcard().field("name").matching(query + "*").constantScore().boost(2))
                        .must(c.match().fields("name", "title", "biographyFull", "biographyShort", "titlePl", "biographyFullPl", "biographyShortPl").matching(query).fuzzy().constantScore().boost(1)))
                .sort(s -> s.score().desc())
                .fetchAll().hits();

        return hits.stream().map(this::toDto).toList();
    }

    public ChampionDto toDto(Champion champion) {
        return ChampionDto.builder()
                .id(champion.getId())
                .slug(champion.getSlug())
                .name(champion.getName())
                .title(champion.getTitle())
                .biographyShort(champion.getBiographyShort())
                .titlePl(champion.getTitlePl())
                .biographyShortPl(champion.getBiographyShortPl())
                .imageUri(champion.getImageUri())
                .faction(Optional.ofNullable(champion.getFaction()).map(Faction::getName).orElse(null))
                .build();
    }

}
