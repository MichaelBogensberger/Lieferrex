package com.htlimst.lieferrex.repository;


import java.util.Optional;
import java.util.Set;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentRepository extends JpaRepository<Fragment,Long> {
    Optional<Fragment> findFragmentById(Long id);
    Set<Fragment> findAllFragmentsByMandant(Mandant mandant);
}
