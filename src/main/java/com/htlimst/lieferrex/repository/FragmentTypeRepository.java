package com.htlimst.lieferrex.repository;


import com.htlimst.lieferrex.model.fragments.FragmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentTypeRepository extends JpaRepository<FragmentType, Long> {
    // Optional<FragmentType> findFragmenttypeByFragment_id(Long id);
    
}
