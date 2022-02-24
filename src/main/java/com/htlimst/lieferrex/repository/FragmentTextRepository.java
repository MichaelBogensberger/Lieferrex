package com.htlimst.lieferrex.repository;

import java.util.Optional;

import com.htlimst.lieferrex.model.Fragmenttext;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FragmentTextRepository extends JpaRepository<Fragmenttext, Long> {
    Optional<Fragmenttext> findFragmenttextByFragment_id(Long id);
}
