package com.htlimst.lieferrex.service.fragmenttype;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlimst.lieferrex.model.fragments.FragmentType;
import com.htlimst.lieferrex.repository.FragmentTypeRepository;

@Service
public class FragmentTypeServiceImpl implements FragmentTypeService {

    @Autowired
    FragmentTypeRepository fragmentTypeRepository;

    @Override
    public Optional<FragmentType> findFragmentTypeByType(String type) {
        return fragmentTypeRepository.findByType(type);
    }

}
