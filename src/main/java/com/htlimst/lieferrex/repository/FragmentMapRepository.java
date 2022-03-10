package com.htlimst.lieferrex.repository;

import java.util.Optional;

import com.htlimst.lieferrex.model.fragments.FragmentMap;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FragmentMapRepository extends JpaRepository<FragmentMap, Long> {
    Optional<FragmentMap> findFragmentmapByFragment_id(Long id);
}

