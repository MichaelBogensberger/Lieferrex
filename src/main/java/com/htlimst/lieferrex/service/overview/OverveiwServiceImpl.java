package com.htlimst.lieferrex.service.overview;

import com.htlimst.lieferrex.controller.gericht.GerichtController;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Seitenaufrufe;
import com.htlimst.lieferrex.model.Umsatz;
import com.htlimst.lieferrex.repository.GerichtBestellungRepository;
import com.htlimst.lieferrex.repository.GerichtRepository;
import com.htlimst.lieferrex.repository.SeitenaufrufeRepository;
import com.htlimst.lieferrex.repository.UmsatzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverveiwServiceImpl implements OverviewService {

    private SeitenaufrufeRepository seitenaufrufeRepository;
    private UmsatzRepository umsatzRepository;
    private GerichtRepository gerichtRepository;
    private GerichtBestellungRepository gerichtBestellungRepository;

    @Autowired
    public OverveiwServiceImpl(SeitenaufrufeRepository seitenaufrufeRepository, UmsatzRepository umsatzRepository, GerichtRepository gerichtRepository, GerichtBestellungRepository gerichtBestellungRepository){
        this.seitenaufrufeRepository = seitenaufrufeRepository;
        this.umsatzRepository = umsatzRepository;
        this.gerichtRepository = gerichtRepository;
        this.gerichtBestellungRepository = gerichtBestellungRepository;
    }

    @Override
    public Seitenaufrufe seitenaufrufe(Mandant mandant) {
        return this.seitenaufrufeRepository.getSeitenaufrufeByMandant(mandant);
    }

    @Override
    public Umsatz umsatz(Mandant mandant) {
        return this.umsatzRepository.getUmsatzByMandant(mandant);
    }

    @Override
    public long gericht() {
        return this.gerichtRepository.countActive();
    }

    @Override
    public long getVerkaufteGerichte(long mandantId) {
        return this.gerichtBestellungRepository.getVerkaufteGerichteByMandant(mandantId);
    }


}
