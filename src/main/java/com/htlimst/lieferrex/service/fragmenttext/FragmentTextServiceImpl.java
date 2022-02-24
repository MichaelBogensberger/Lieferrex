package com.htlimst.lieferrex.service.fragmenttext;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Fragmenttext;
import com.htlimst.lieferrex.repository.FragmentRepository;
import com.htlimst.lieferrex.repository.FragmentTextRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.mandant.MandantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FragmentTextServiceImpl implements FragmentTextService {

    @Autowired
    private FragmentTextRepository fragmenttextRepository;

    // @Autowired
    // private MandantRepository mandantRepository;

    // @Override
    // public List<Mandant> alleMandanten(){
    //     return null;
    // }

    // @Override
    // public Mandant mandantEinfuegen(Mandant mandant){
    //     return null;

    // }

    // @Override
    // public Mandant mandantMitId(Long id){
    //     return null;

    // }

    // @Override
    // public void mandantLoeschenMitId(Long id){

    // }

    @Override
    public Optional<Fragmenttext> findFragmenttextByFragment_id(Long id){
        return fragmenttextRepository.findFragmenttextByFragment_id(id);
    }

}
