package com.htlimst.lieferrex.service.overview;

import com.htlimst.lieferrex.dto.MandantRegistrationDto;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Seitenaufrufe;
import com.htlimst.lieferrex.model.Umsatz;

import java.util.List;
import java.util.Optional;

public interface OverviewService {
    public Seitenaufrufe seitenaufrufe(Mandant mandant);
    public Umsatz umsatz(Mandant mandant);
    public long gericht();
    public long getVerkaufteGerichte(long mandantId);
}
