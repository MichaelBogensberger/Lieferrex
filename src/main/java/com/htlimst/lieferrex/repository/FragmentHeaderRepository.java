package com.htlimst.lieferrex.repository;

import java.util.Optional;

import com.htlimst.lieferrex.model.fragments.FragmentHeader;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FragmentHeaderRepository extends JpaRepository<FragmentHeader, Long> {
    Optional<FragmentHeader> findFragmentheaderByFragment_id(Long id);
}
