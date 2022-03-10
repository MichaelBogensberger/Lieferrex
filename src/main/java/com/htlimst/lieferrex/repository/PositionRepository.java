package com.htlimst.lieferrex.repository;

import org.springframework.stereotype.Repository;
import com.htlimst.lieferrex.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
    
}
