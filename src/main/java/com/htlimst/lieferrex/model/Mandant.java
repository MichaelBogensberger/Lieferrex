package com.htlimst.lieferrex.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "mandant")
public class Mandant {

        @Id
        @Column(name = "mandant_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotEmpty
        private String firmenname;
        @NotEmpty
        private String land;
        @NotEmpty
        private String ort;

        // @Size(min = 4, max = 6)
        private String plz;

        private String strasse;
        private String hausnummer;
        private String telefonnummer;

        private double umsatz_summe;
        private int seitenaufrufe_summe;

        private String akzentFarbe;

        // @Email
        private String email;

        private double mindestbestellwert;
        private double lieferkosten;
        private int durchschnittsLieferZeit;
        private int durchschnittsAbholZeit;

        @ManyToOne
        @JoinColumn(name = "kategorie_id")
        private Kategorie kategorie;

        @ManyToMany
        @JoinTable(name = "mandant_bestellart", joinColumns = @JoinColumn(name = "mandant_id"), inverseJoinColumns = @JoinColumn(name = "bestellart_id"))
        private Set<Bestellart> bestellart;

        @OneToMany(mappedBy = "mandant")
        private Set<Gericht> gerichte;

        @OneToMany(mappedBy = "mandant")
        private Set<Bestellung> bestellungen;

        @OneToMany(mappedBy = "mandant")
        private Set<Angestellter> angestellte;

        @OneToMany(mappedBy = "mandant")
        private Set<Umsatz> umsatz;

        @OneToMany(mappedBy = "mandant")
        private Set<Fragment> fragmente;

        @ManyToOne
        @JoinColumn(name = "layout_id")
        private Layout layout;

        @OneToOne(mappedBy = "mandant")
        private AboutUs aboutus;

        @OneToOne(mappedBy = "mandant")
        private Gallery gallery;

        @OneToOne
        @JoinColumn(name = "geo_position_id", referencedColumnName = "geo_position_id")
        private GeoPosition geoPosition;

        public Mandant(Long id, String firmenname, String land, String ort, String plz, String strasse,
                        String hausnummer, String telefonnummer, double umsatz_summe, int seitenaufrufe_summe,
                        int durchschnittsAbholZeit, int durchschnittsLieferZeit, String email,
                        double mindestbestellwert, double lieferkosten, Kategorie kategorie, Set<Bestellart> bestellart,
                        Layout layout, GeoPosition geoPosition) {
                this.id = id;
                this.firmenname = firmenname;
                this.land = land;
                this.ort = ort;
                this.plz = plz;
                this.strasse = strasse;
                this.hausnummer = hausnummer;
                this.telefonnummer = telefonnummer;
                this.umsatz_summe = umsatz_summe;
                this.seitenaufrufe_summe = seitenaufrufe_summe;
                this.durchschnittsAbholZeit = durchschnittsAbholZeit;
                this.durchschnittsLieferZeit = durchschnittsLieferZeit;
                this.email = email;
                this.mindestbestellwert = mindestbestellwert;
                this.lieferkosten = lieferkosten;
                this.kategorie = kategorie;
                this.bestellart = bestellart;
                this.layout = layout;
                this.geoPosition = geoPosition;
        }
}
