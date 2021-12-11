package com.htlimst.lieferrex.repository;


import com.htlimst.lieferrex.model.Gericht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerichtRepository extends JpaRepository<Gericht,Long> {
}
