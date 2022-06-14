package com.htlimst.lieferrex.repository;

import java.util.Optional;

import com.htlimst.lieferrex.model.fragments.FragmentText;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FragmentTextRepository extends JpaRepository<FragmentText, Long> {
    Optional<FragmentText> findFragmenttextByFragment_id(Long id);
    FragmentText save(FragmentText fragmentText);
}
