package com.htlimst.lieferrex.service.fragmentheader;

import com.htlimst.lieferrex.model.fragments.FragmentHeader;
import java.util.Optional;

public interface FragmentHeaderService {

    Optional<FragmentHeader> findFragmentheaderByFragment_id(Long id);
    FragmentHeader save(FragmentHeader fragmentHeader);
    void delete(FragmentHeader fragmentHeader);

}
