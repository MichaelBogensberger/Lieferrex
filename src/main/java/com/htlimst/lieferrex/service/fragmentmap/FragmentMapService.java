package com.htlimst.lieferrex.service.fragmentmap;

import com.htlimst.lieferrex.model.Fragmentmap;

import java.util.List;
import java.util.Optional;

public interface FragmentMapService {

    Optional<Fragmentmap> findFragmentmapByFragment_id(Long id);

}
