package com.ceglarz.leaguepediaapi.champion;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChampionDto(
        UUID id,
        String slug,
        String name,
        String title,
        String biographyShort,
        String titlePl,
        String biographyShortPl,
        String imageUri,
        String faction
) {
}
