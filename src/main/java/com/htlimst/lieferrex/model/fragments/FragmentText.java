package com.htlimst.lieferrex.model.fragments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import com.htlimst.lieferrex.model.Fragment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fragmenttext")
public class FragmentText {

    @Id
    @Column(name = "fragmenttext_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String titel;

    @NotEmpty
    private String text;

    @OneToOne
    @JoinColumn(name = "fragment_id")
    private Fragment fragment;

}
