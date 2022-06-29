package com.htlimst.lieferrex.service.gallery;

import java.util.Optional;

import com.htlimst.lieferrex.model.Gallery;
import com.htlimst.lieferrex.model.Mandant;

public interface GalleryService {
    Gallery save(Gallery gallery);

    void delete(Gallery gallery);

    Optional<Gallery> findGalleryByMandant(Mandant mandant);
}
