package com.htlimst.lieferrex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.htlimst.lieferrex.model.Gallery;
import com.htlimst.lieferrex.model.Mandant;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Gallery save(Gallery gallery);

    void delete(Gallery gallery);

    Optional<Gallery> findGalleryByMandant(Mandant mandant);
}
