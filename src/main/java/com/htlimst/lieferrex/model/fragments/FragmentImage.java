package com.htlimst.lieferrex.model.fragments;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import com.htlimst.lieferrex.model.Fragment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fragmentimage")
public class FragmentImage {


    @Id
    @Column(name = "fragmentimage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String titel;

    @Lob
    private byte[] imageBlob;

    @OneToOne
    @JoinColumn(name="fragment_id")
    private Fragment fragment;

}
