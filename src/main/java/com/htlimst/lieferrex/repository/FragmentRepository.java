package com.htlimst.lieferrex.repository;

import java.util.List;
import java.util.Optional;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentRepository extends JpaRepository<Fragment, Long> {
    List<Fragment> findFragmentByMandant_id(Long id);
    Optional<Fragment> findFragmentByMandant_idAndPosition_name(Long id, String position);
    Fragment save(Fragment fragment);
}
