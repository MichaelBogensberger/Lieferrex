package com.htlimst.lieferrex.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "geo_position")
public class GeoPosition {

    @Id
    @Column(name = "geo_position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "geoPosition")
    private Mandant mandant;

    private double geoLat;

    private double geoLng;

    public GeoPosition(Long id, double geoLat, double geoLng) {
        this.id = id;
        this.geoLat = geoLat;
        this.geoLng = geoLng;
    }
}
