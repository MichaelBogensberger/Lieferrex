package com.htlimst.lieferrex.repository;


import java.util.Optional;

import com.htlimst.lieferrex.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template,Long> {
    Optional<Template> findTemplateById(Long id);
}
