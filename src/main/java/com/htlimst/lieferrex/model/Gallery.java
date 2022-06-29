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
@Table(name = "gallery")
public class Gallery {

    @Id
    @Column(name = "gallery_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String title;

    @Lob
    private byte[] imageBlobOne;

    @Lob
    private byte[] imageBlobTwo;

    @Lob
    private byte[] imageBlobThree;

    @Lob
    private byte[] imageBlobFour;

    @Lob
    private byte[] imageBlobFive;

    @OneToOne
    @JoinColumn(name = "mandant_id")
    private Mandant mandant;

}
