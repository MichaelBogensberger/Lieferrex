package com.htlimst.lieferrex.repository;

import com.htlimst.lieferrex.model.GeoPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoPositionRepository extends JpaRepository<GeoPosition,Long> {
}
