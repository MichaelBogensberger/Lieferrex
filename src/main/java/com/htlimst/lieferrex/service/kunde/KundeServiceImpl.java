package com.htlimst.lieferrex.service.kunde;

import com.htlimst.lieferrex.dto.KundeRegistrationDto;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.RolleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class KundeServiceImpl implements KundeService{

    private KundeRepository kundeRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RolleRepository rolleRepository;

    @Autowired
    public KundeServiceImpl(KundeRepository kundeRepository, BCryptPasswordEncoder passwordEncoder, RolleRepository rolleRepository) {
        this.kundeRepository = kundeRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolleRepository = rolleRepository;
    }




    @Override
    public Kunde save(KundeRegistrationDto registrationDto) {
        Kunde kunde = new Kunde();


        return kundeRepository.save(kunde);
    }
}
