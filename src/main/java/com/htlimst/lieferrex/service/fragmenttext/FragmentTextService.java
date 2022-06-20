package com.htlimst.lieferrex.service.fragmenttext;

import com.htlimst.lieferrex.model.fragments.FragmentText;
import java.util.Optional;

public interface FragmentTextService {

    Optional<FragmentText> findFragmenttextByFragment_id(Long id);
    FragmentText save(FragmentText fragmentText);
    void delete(FragmentText fragmentText);

}
