package com.htlimst.lieferrex.repository;


import java.util.Optional;

import com.htlimst.lieferrex.model.Fragmenttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmenttypeRepository extends JpaRepository<Fragmenttype,Long> {
    Optional<Fragmenttype> findFragmanttypeById(Long id);
}
