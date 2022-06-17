package com.htlimst.lieferrex.service.fragmentimage;

import com.htlimst.lieferrex.model.fragments.FragmentImage;
import com.htlimst.lieferrex.repository.FragmentImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FragmentImageServiceImpl implements FragmentImageService {

    @Autowired
    private FragmentImageRepository fragmentimageRepository;

    @Override
    public Optional<FragmentImage> findFragmentimageByFragment_id(Long id){
        return fragmentimageRepository.findFragmentimageByFragment_id(id);
    }

    @Override
    public void saveFragmentImage(FragmentImage fragmentImage) {
        fragmentimageRepository.save(fragmentImage);        
    }

    @Override
    public FragmentImage save(FragmentImage fragmentImage) {
        return fragmentimageRepository.save(fragmentImage);
    }

}
