package com.htlimst.lieferrex.service.fragmentimage;

import com.htlimst.lieferrex.model.fragments.FragmentImage;
import java.util.Optional;

public interface FragmentImageService {

    Optional<FragmentImage> findFragmentimageByFragment_id(Long id);
    void saveFragmentImage(FragmentImage fragmentImage);
    FragmentImage save(FragmentImage fragmentImage);

}
