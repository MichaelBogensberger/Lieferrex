package com.htlimst.lieferrex.repository;


import com.htlimst.lieferrex.model.fragments.FragmentType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentTypeRepository extends JpaRepository<FragmentType, Long> {
    // Optional<FragmentType> findFragmenttypeByFragment_id(Long id);
    Optional<FragmentType> findByType(String type);
    
}
