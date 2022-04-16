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
@Table(name = "fragmentheader")
public class FragmentHeader {


    @Id
    @Column(name = "fragmentheader_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String titel;

    @NotEmpty
    private String text;

    @NotEmpty
    private String image_path;

    @OneToOne
    @JoinColumn(name="fragment_id")
    private Fragment fragment;

}
