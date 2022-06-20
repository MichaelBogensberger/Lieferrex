package com.htlimst.lieferrex.service.fragmentheader;

import com.htlimst.lieferrex.model.fragments.FragmentHeader;
import com.htlimst.lieferrex.repository.FragmentHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FragmentHeaderServiceImpl implements FragmentHeaderService {

    @Autowired
    private FragmentHeaderRepository fragmentheaderRepository;

    @Override
    public Optional<FragmentHeader> findFragmentheaderByFragment_id(Long id){
        return fragmentheaderRepository.findFragmentheaderByFragment_id(id);
    }

    @Override
    public FragmentHeader save(FragmentHeader fragmentHeader) {
        return fragmentheaderRepository.save(fragmentHeader);
    }

    @Override
    public void delete(FragmentHeader fragmentHeader) {
        fragmentheaderRepository.delete(fragmentHeader);        
    }

    

}
