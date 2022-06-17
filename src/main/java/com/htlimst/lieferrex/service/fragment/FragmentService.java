package com.htlimst.lieferrex.service.fragment;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Position;

import java.util.List;
import java.util.Optional;

public interface FragmentService {

    List<Fragment> findFragmentByMandant_id(Long id);
    Optional<Fragment> findFragmentByMandant_idAndPosition_name(Long id, String position);
    Fragment save(Fragment fragment);
    void delete(Fragment fragment);

}
