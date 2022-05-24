package com.htlimst.lieferrex.service.angestellter;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Mandant;

import java.util.List;
import java.util.Optional;

public interface AngestellterService {

    Angestellter findByEmail(String email);

    public List<Angestellter> alleAngestellen();
    public Optional<Angestellter> apiGetAngestellterById(Long id);
    public long countAngestellter();
    public long countAdmins();
    public long countZugang();
    public void saveAngestellter(Angestellter angestellter, String rollen, Mandant mandant);
    public Optional<Angestellter> findAngestellterByToken(String token);
    public void saveAngestellter(Angestellter angestellter);

}
