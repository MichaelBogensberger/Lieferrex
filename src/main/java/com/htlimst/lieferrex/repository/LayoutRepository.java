package com.htlimst.lieferrex.repository;

import org.springframework.stereotype.Repository;
import com.htlimst.lieferrex.model.Layout;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LayoutRepository extends JpaRepository<Layout,Long> {
    Optional<Layout> findLayoutByName(String name);
}
