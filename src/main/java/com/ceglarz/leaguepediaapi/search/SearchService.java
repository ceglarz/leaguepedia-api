package com.ceglarz.leaguepediaapi.search;

import com.ceglarz.leaguepediaapi.champion.Champion;
import com.ceglarz.leaguepediaapi.champion.ChampionDto;
import com.ceglarz.leaguepediaapi.faction.Faction;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final EntityManager entityManager;

    public List<ChampionDto> findByName(String name) {
        return entityManager.createQuery("SELECT c FROM Champion c WHERE lower(c.name) like lower('%'||:name||'%')", Champion.class)
                .setParameter("name", name)
                .getResultList()
                .stream().map(this::toDto)
                .toList();
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
                .faction(Optional.ofNullable(champion.getFaction())
                        .map(Faction::getName).orElse(null))
                .build();
    }

}
