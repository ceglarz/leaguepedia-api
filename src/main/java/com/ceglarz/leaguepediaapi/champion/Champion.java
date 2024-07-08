package com.ceglarz.leaguepediaapi.champion;

import com.ceglarz.leaguepediaapi.faction.Faction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.util.UUID;

@Entity
@Getter
@Setter
@Indexed
public class Champion {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String slug;

    @FullTextField
    private String name;

    @FullTextField
    private String title;

    @FullTextField
    @Column(columnDefinition = "TEXT")
    private String biographyFull;

    @FullTextField
    @Column(columnDefinition = "TEXT")
    private String biographyShort;

    @FullTextField
    private String quote;

    @FullTextField
    private String titlePl;

    @FullTextField
    @Column(columnDefinition = "TEXT")
    private String biographyFullPl;

    @FullTextField
    @Column(columnDefinition = "TEXT")
    private String biographyShortPl;

    @FullTextField
    private String quotePl;

    private String imageUri;

    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "faction_id")
    private Faction faction;

}
