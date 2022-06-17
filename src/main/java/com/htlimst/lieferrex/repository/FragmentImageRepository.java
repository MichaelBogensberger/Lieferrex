package com.htlimst.lieferrex.repository;

import java.util.Optional;

import com.htlimst.lieferrex.model.fragments.FragmentImage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FragmentImageRepository extends JpaRepository<FragmentImage, Long> {
    Optional<FragmentImage> findFragmentimageByFragment_id(Long id);
    FragmentImage save(FragmentImage fragmentImage);
    void delete(FragmentImage fragmentImage);
}
