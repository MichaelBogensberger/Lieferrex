package com.htlimst.lieferrex.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.htlimst.lieferrex.model.fragments.FragmentHeader;
import com.htlimst.lieferrex.model.fragments.FragmentImage;
import com.htlimst.lieferrex.model.fragments.FragmentMap;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.model.fragments.FragmentType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fragment")
public class Fragment {


    @Id
    @Column(name = "fragment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name="mandant_id")
    private Mandant mandant;

    @ManyToOne
    @JoinColumn(name="fragmenttype_id")
    private FragmentType fragmenttype;

    @OneToOne(mappedBy = "fragment")
    private FragmentText fragmenttext;

    @OneToOne(mappedBy = "fragment")
    private FragmentMap fragmentmap;

    @OneToOne(mappedBy = "fragment")
    private FragmentHeader fragmentheader;

    @OneToOne(mappedBy = "fragment")
    private FragmentImage fragmentimage;


}
