package com.ceglarz.leaguepediaapi.faction;

import com.ceglarz.leaguepediaapi.champion.Champion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Indexed
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String slug;

    @FullTextField
    private String name;

    @FullTextField
    @Column(columnDefinition = "TEXT")
    private String overview;

    private String imageUri;

    @FullTextField
    private String namePl;

    @FullTextField
    @Column(columnDefinition = "TEXT")
    private String overviewPl;

    @OneToMany(mappedBy = "faction")
    private Set<Champion> champions;

}
