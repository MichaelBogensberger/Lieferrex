package com.htlimst.lieferrex.service.angestellter;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AngestellterServiceImpl implements AngestellterService{

    @Autowired
    AngestellterRepository angestellterRepository;

    @Override
    public Angestellter findByEmail(String email) {
        return angestellterRepository.findByEmail(email);
    }

}
