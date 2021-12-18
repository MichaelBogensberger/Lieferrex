package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.Gerichtstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerichtstatusRepository extends JpaRepository<Gerichtstatus,Long> {
}
