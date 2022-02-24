package com.htlimst.lieferrex.repository;

import java.util.Optional;

import com.htlimst.lieferrex.model.Fragmentmap;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FragmentMapRepository extends JpaRepository<Fragmentmap, Long> {
    Optional<Fragmentmap> findFragmentmapByFragment_id(Long id);
}

