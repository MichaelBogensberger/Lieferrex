package com.htlimst.lieferrex.service.mandant;

import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.mandant.MandantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MandantServiceImpl implements MandantService {

    @Autowired
    private MandantRepository mandantRepository;

    @Override
    public List<Mandant> alleMandanten(){
        return null;
    }

    @Override
    public Mandant mandantEinfuegen(Mandant mandant){
        return null;

    }

    @Override
    public Mandant mandantMitId(Long id){
        return null;

    }

    @Override
    public void mandantLoeschenMitId(Long id){

    }

    @Override
    public Optional<Mandant> findMandantByFirmenname(String name){
        return mandantRepository.findMandantByFirmenname(name);
    }

    @Override
    public Mandant listAll() {
        return mandantRepository.getById(3L);
    }

}
