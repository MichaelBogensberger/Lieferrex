package com.htlimst.lieferrex.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "aboutus")
public class AboutUs {


    @Id
    @Column(name = "aboutus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String textOne;

    @NotEmpty
    private String textTwo;

    @Lob
    private byte[] imageBlobOne;

    @Lob
    private byte[] imageBlobTwo;

    @OneToOne
    @JoinColumn(name="mandant_id")
    private Mandant mandant;

}
