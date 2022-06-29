package com.htlimst.lieferrex.service.angestellter;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Rolle;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.repository.RolleRepository;
import com.htlimst.lieferrex.service.mandant.MandantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AngestellterServiceImpl implements AngestellterService{

    @Autowired
    AngestellterRepository angestellterRepository;

    @Autowired
    RolleRepository rolleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MandantRepository mandantRepository;

    @Override
    public Angestellter findByEmail(String email) {
        return angestellterRepository.findByEmail(email);
    }

    @Override
    public List<Angestellter> alleAngestellen(Long mandantId) {
        return angestellterRepository.getAllByMandant_Id(mandantId);
    }

    @Override
    public Optional<Angestellter> apiGetAngestellterById(Long id) {
        return angestellterRepository.findById(id);
    }

    @Override
    public long countAngestellter(long mandantId) {
        return angestellterRepository.countAngestellte(mandantId);
    }

    @Override
    public long countAdmins(long mandantId) {
        return angestellterRepository.countAdmins(mandantId);
    }

    @Override
    public long countZugang() {
        return angestellterRepository.count();
    }

    @Override
    public void saveAngestellter(Angestellter angestellter, String rollen, Mandant mandant) {
        if(rollen.equals("angestellter")){
            angestellter.setRolle(Arrays.asList(rolleRepository.findByRolle("ROLE_ANGESTELLTER")));
        }else if(rollen.equals("admin")){
            angestellter.setRolle(Arrays.asList(rolleRepository.findByRolle("ROLE_MANDANT")));
        }
        angestellter.setMandant(mandant);
        angestellter.setPasswort(passwordEncoder.encode(angestellter.getPasswort()));
        angestellterRepository.save(angestellter);
    }

    @Override
    public Optional<Angestellter> findAngestellterByToken(String token) {
        return angestellterRepository.findAngestellterByToken(token);
    }

    @Override
    public void saveAngestellter(Angestellter angestellter) {
        angestellterRepository.save(angestellter);
    }

}
