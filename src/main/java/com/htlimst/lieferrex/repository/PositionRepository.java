package com.htlimst.lieferrex.repository;

import org.springframework.stereotype.Repository;

import com.htlimst.lieferrex.model.Layout;
import com.htlimst.lieferrex.model.Position;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
    Optional<Position> findByNameAndLayout(String name, Layout layout);
}
