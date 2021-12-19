package com.htlimst.lieferrex.repository;


import com.htlimst.lieferrex.model.Fragmenttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmenttypeRepository extends JpaRepository<Fragmenttype,Long> {
}
