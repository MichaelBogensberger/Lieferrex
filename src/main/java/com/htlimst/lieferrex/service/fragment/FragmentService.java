package com.htlimst.lieferrex.service.fragment;

import java.util.Set;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;

public interface FragmentService {
    Fragment getFragmentById(Long id);

    Set<Fragment> getAllFragmentsByMandant(Mandant mandant);
}
