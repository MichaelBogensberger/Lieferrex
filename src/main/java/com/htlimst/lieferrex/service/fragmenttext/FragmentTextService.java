package com.htlimst.lieferrex.service.fragmenttext;

import com.htlimst.lieferrex.model.fragments.FragmentText;
import java.util.Optional;

public interface FragmentTextService {

    Optional<FragmentText> findFragmenttextByFragment_id(Long id);
    void saveFragmentText(FragmentText fragmentText);
    FragmentText save(FragmentText fragmentText);

}
