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
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Get champions by name")
    @GetMapping("/champions")
    public List<ChampionDto> searchChampion(@RequestParam String name) {
        return searchService.findByName(name);
    }

}
