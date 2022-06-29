package com.htlimst.lieferrex.service.aboutus;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlimst.lieferrex.model.AboutUs;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.AboutUsRepository;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    AboutUsRepository aboutUsRepository;

    @Override
    public void delete(AboutUs aboutUs) {
        aboutUsRepository.delete(aboutUs);
    }

    @Override
    public AboutUs save(AboutUs aboutUs) {
        return aboutUsRepository.save(aboutUs);
    }

    @Override
    public Optional<AboutUs> findAboutusByMandant(Mandant mandant) {
        return aboutUsRepository.findAboutusByMandant(mandant);
    }

}
