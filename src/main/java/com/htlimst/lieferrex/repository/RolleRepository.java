package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Rolle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolleRepository extends JpaRepository<Rolle,Long> {
    List<Rolle> findByRolle(Rolle rolle);

    Rolle findByRolle(String rolle);
}
