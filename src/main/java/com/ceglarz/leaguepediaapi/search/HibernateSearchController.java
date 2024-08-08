package com.ceglarz.leaguepediaapi.search;

import com.ceglarz.leaguepediaapi.champion.ChampionDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HibernateSearchController {

    private final HibernateSearchService hibernateSearchService;

    @GetMapping("/rebuild-index")
    public void rebuildIndex() {
        hibernateSearchService.rebuildIndex();
    }


    @Operation(summary = "Get all champions if u remember whatever")
    @GetMapping("/champions-whatever")
    public List<ChampionDto> searchChampionsWhatever(@RequestParam String query) {
        return hibernateSearchService.findChampionsWhatever(query);
    }

    @Operation(summary = "Get champions by every field, but name first")
    @GetMapping("/champions-name-first")
    public List<ChampionDto> searchChampionsNameFirst(@RequestParam String query) {
        return hibernateSearchService.findChampionsNameFirst(query);
    }

    @Operation(summary = "Get champions, but filter by faction")
    @GetMapping("/champions-with-faction")
    public List<ChampionDto> hibernateSearchChampionFilterFaction(@RequestParam String query, @RequestParam(required = false) String faction) {
        return hibernateSearchService.findChampionsFilterFaction(query, faction);
    }

    @GetMapping("/champions-complex")
    public List<ChampionDto> hibernateSearchChampion(@RequestParam String query) {
        return hibernateSearchService.findChampionsComplex(query);
    }

}
