package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Rolle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolleRepository extends JpaRepository<Rolle,Long> {
}
