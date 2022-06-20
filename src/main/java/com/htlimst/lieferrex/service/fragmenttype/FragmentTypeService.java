package com.htlimst.lieferrex.service.fragmenttype;

import java.util.Optional;

import com.htlimst.lieferrex.model.fragments.FragmentType;

public interface FragmentTypeService {
    Optional<FragmentType> findFragmentTypeByType(String type);
}
