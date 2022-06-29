package com.htlimst.lieferrex.repository;


import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AngestellterRepository extends JpaRepository<Angestellter,Long> {
    Angestellter findByEmail(String email);
    List<Angestellter> getAllByEmail(String email);
    void deleteAllByEmail(String s);
    @Query(value = "SELECT COUNT(angestellter_rolle.angestellter_id) FROM angestellter_rolle INNER JOIN rolle ON angestellter_rolle.rolle_id = rolle.rolle_id INNER JOIN angestellter ON angestellter.angestellter_id = angestellter_rolle.angestellter_id  WHERE rolle.rolle_id = 2 AND mandant_id = ?1", nativeQuery = true)
    public long countAdmins(long mandantId);
    @Query(value = "SELECT COUNT(angestellter_rolle.angestellter_id) FROM angestellter_rolle INNER JOIN rolle ON angestellter_rolle.rolle_id = rolle.rolle_id INNER JOIN angestellter ON angestellter.angestellter_id = angestellter_rolle.angestellter_id  WHERE rolle.rolle_id = 3 AND mandant_id = ?1", nativeQuery = true)
    public long countAngestellte(long mandantId);
    Optional<Angestellter> findAngestellterByToken(String token);
    List<Angestellter> getAllByMandant_Id(Long mandanid);

}
