package com.htlimst.lieferrex.service.gallery;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlimst.lieferrex.model.Gallery;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.GalleryRepository;

@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    GalleryRepository galleryRepository;

    @Override
    public void delete(Gallery gallery) {
        galleryRepository.delete(gallery);
    }

    @Override
    public Optional<Gallery> findGalleryByMandant(Mandant mandant) {
        return galleryRepository.findGalleryByMandant(mandant);
    }

    @Override
    public Gallery save(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

}
