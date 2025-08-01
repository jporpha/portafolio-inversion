package com.jp.orpha.portafolio_inversion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
public class AssetEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "asset")
    private List<HoldingEntity> holdings;

    @OneToMany(mappedBy = "asset")
    private List<PriceEntity> prices;

    @OneToMany(mappedBy = "asset")
    private List<WeightEntity> weights;
}
