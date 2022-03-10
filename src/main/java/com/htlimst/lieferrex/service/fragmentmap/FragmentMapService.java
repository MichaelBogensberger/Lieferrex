package com.htlimst.lieferrex.service.fragmentmap;

import com.htlimst.lieferrex.model.fragments.FragmentMap;
import java.util.Optional;

public interface FragmentMapService {

    Optional<FragmentMap> findFragmentmapByFragment_id(Long id);

}
