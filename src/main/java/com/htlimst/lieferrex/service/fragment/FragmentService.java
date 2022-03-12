package com.htlimst.lieferrex.service.fragment;

import com.htlimst.lieferrex.model.Fragment;
import java.util.List;

public interface FragmentService {

    List<Fragment> findFragmentByMandant_id(Long id);

}
