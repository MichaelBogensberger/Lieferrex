package com.htlimst.lieferrex.service.fragment;

import java.util.Set;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.FragmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FragmentServiceImpl implements FragmentService {
    
    @Autowired
    private FragmentRepository fragmentRepository;

    public Fragment getFragmentById(Long id){
        return fragmentRepository.findFragmentById(id).get();
    }

    public Set<Fragment> getAllFragmentsByMandant(Mandant mandant){
        return fragmentRepository.findAllFragmentsByMandant(mandant);
    }
}
