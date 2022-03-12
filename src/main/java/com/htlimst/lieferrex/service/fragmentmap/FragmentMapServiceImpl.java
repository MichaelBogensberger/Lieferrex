package com.htlimst.lieferrex.service.fragmentmap;

import com.htlimst.lieferrex.model.fragments.FragmentMap;
import com.htlimst.lieferrex.repository.FragmentMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FragmentMapServiceImpl implements FragmentMapService {

    @Autowired
    private FragmentMapRepository fragmentmapRepository;

    @Override
    public Optional<FragmentMap> findFragmentmapByFragment_id(Long id){
        return fragmentmapRepository.findFragmentmapByFragment_id(id);
    }

}
