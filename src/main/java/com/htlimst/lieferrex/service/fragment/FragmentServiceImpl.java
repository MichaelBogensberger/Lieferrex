package com.htlimst.lieferrex.service.fragment;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Position;
import com.htlimst.lieferrex.repository.FragmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FragmentServiceImpl implements FragmentService {

    @Autowired
    private FragmentRepository fragmentRepository;

    
    @Override
    public List<Fragment> findFragmentByMandant_id(Long id){
        return fragmentRepository.findFragmentByMandant_id(id);
    }

    @Override
    public Optional<Fragment> findFragmentByMandant_idAndPosition_name(Long id, String position){
        return fragmentRepository.findFragmentByMandant_idAndPosition_name(id, position);
    }

    @Override
    public Fragment save(Fragment fragment) {
        return fragmentRepository.save(fragment);
    }

    @Override
    public void delete(Fragment fragment) {
        fragmentRepository.delete(fragment);
    }

}
