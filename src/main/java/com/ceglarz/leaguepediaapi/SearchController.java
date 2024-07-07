package com.ceglarz.leaguepediaapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @GetMapping
    public String searchChampion() {
        return "hello!";
    }

}
