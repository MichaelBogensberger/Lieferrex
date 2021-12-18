package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Bestellart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestellartRepository extends JpaRepository<Bestellart,Long>{
}
