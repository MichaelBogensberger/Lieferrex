package com.htlimst.lieferrex.service.fragmenttext;

import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.repository.FragmentTextRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FragmentTextServiceImpl implements FragmentTextService {

    @Autowired
    private FragmentTextRepository fragmenttextRepository;

    @Override
    public Optional<FragmentText> findFragmenttextByFragment_id(Long id){
        return fragmenttextRepository.findFragmenttextByFragment_id(id);
    }

    @Override
    public void saveFragmentText(FragmentText fragmentText) {
        fragmenttextRepository.save(fragmentText);        
    }

    @Override
    public FragmentText save(FragmentText fragmentText) {
        return fragmenttextRepository.save(fragmentText);
    }

}
