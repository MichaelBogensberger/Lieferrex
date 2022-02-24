package com.htlimst.lieferrex.service.fragmenttext;

import com.htlimst.lieferrex.model.Fragmenttext;

import java.util.List;
import java.util.Optional;

public interface FragmentTextService {

    Optional<Fragmenttext> findFragmenttextByFragment_id(Long id);

}
