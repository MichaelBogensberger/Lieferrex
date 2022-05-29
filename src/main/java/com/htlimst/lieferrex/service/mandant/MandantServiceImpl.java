package com.htlimst.lieferrex.service.mandant;

import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.dto.MandantSuchDto;
import com.htlimst.lieferrex.exceptions.MandantNotFoundException;
import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Rolle;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.repository.RolleRepository;


import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MandantServiceImpl implements MandantService {

    private AngestellterRepository angestellterRepository;
    private MandantRepository mandantRepository;
    private RolleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MandantServiceImpl(AngestellterRepository angestellterRepository, MandantRepository mandantRepository, RolleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.angestellterRepository = angestellterRepository;
        this.mandantRepository = mandantRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<Mandant> alleMandanten() {
        return null;
    }

    @Override
    public Mandant mandantEinfuegen(Mandant mandant) {
        return null;
    }

    @Override
    public Mandant mandantMitId(Long id) {
        return mandantRepository.getById(id);
    }

    @Override
    public void mandantLoeschenMitId(Long id) {
    }

    @Override
    public Optional<Mandant> findMandantByFirmenname(String name) {
        return mandantRepository.findMandantByFirmenname(name);
    }

    @Override
    public Optional<Mandant> findMandantByAngestellterEmail(String email) {
        return mandantRepository.findMandantByAngestellte_Email(email);
    }

    @Override
    public Mandant listAll() {
        return mandantRepository.getById(3L);
    }

    @Override
    public void save(Mandant mandant) {
        mandantRepository.save(mandant);
    }


    @Override
    public Mandant saveRegistrationDto(MandantRegistrationDto mandantRegistrationDto) {
        Mandant mandant = new Mandant().builder().
                firmenname(mandantRegistrationDto.getFirmenname()).
                land(mandantRegistrationDto.getLand()).
                ort(mandantRegistrationDto.getOrt()).
                plz(mandantRegistrationDto.getPlz()).
                strasse(mandantRegistrationDto.getStrasse()).
                hausnummer(mandantRegistrationDto.getHausnummer()).
                telefonnummer(mandantRegistrationDto.getTelefonnummer()).
                email(mandantRegistrationDto.getEmail()).
                mindestbestellwert(mandantRegistrationDto.getMindestbestellwert()).
                lieferkosten(mandantRegistrationDto.getLieferkosten()).build();
        mandantRepository.save(mandant);


        Angestellter angestellter = new Angestellter().builder().
                mandant(mandant).
                vorname(mandantRegistrationDto.getVorname()).
                nachname(mandantRegistrationDto.getNachname()).
                email(mandantRegistrationDto.getEmail()).
                passwort(passwordEncoder.encode(mandantRegistrationDto.getPasswort())).
                rolle(Arrays.asList(roleRepository.findByRolle("ROLE_MANDANT"))).build();
        angestellterRepository.save(angestellter);

        return null;
    }

    //throws mandant not found exception
    public List<MandantSuchDto> findMandantByOrt(String Ort) throws MandantNotFoundException {
        List<Mandant> mandantenList = mandantRepository.findMandantByOrt(Ort);
        if (!mandantenList.isEmpty()) {
            List<MandantSuchDto> mandantSuchDtoList = new ArrayList<>();
            for (Mandant mandant: mandantenList) {
                mandantSuchDtoList.add(new MandantSuchDto().builder()
                        .firmenname(mandant.getFirmenname())
                        .ort(mandant.getOrt())
                        .adresse(mandant.getStrasse() + " " + mandant.getHausnummer())
                        .rating(3)
                        .build());
            }
            return mandantSuchDtoList;
        }
        else {
            System.out.println("test");
            throw new MandantNotFoundException();
        }

    }


}
