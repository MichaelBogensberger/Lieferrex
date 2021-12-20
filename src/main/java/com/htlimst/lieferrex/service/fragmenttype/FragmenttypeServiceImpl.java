package com.htlimst.lieferrex.service.fragmenttype;

import com.htlimst.lieferrex.model.Fragmenttype;
import com.htlimst.lieferrex.repository.FragmenttypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FragmenttypeServiceImpl implements FragmenttypeService {
    
    @Autowired
    private FragmenttypeRepository fragmenttypeRepository;

    public Fragmenttype getFragmenttypeById(Long id){
        return fragmenttypeRepository.findFragmanttypeById(id).get();
    }
}
