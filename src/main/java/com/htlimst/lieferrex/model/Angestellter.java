package com.htlimst.lieferrex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "angestellter")
public class Angestellter {

    @Id
    @Column(name = "angestellter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="mandant_id", nullable=false)
    private Mandant mandant;

    private String vorname;
    private String nachname;
    private String email;
    private String passwort;

    private String token;


    @JsonIgnoreProperties
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "angestellter_rolle",
            joinColumns = @JoinColumn(name = "angestellter_id"),
            inverseJoinColumns = @JoinColumn(name = "rolle_id"))
    private Collection<Rolle> rolle;

    public void removeRolle(Rolle rolle){
        this.rolle.remove(rolle);
    }

}

