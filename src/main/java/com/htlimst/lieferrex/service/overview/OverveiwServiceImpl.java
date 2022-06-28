package com.htlimst.lieferrex.service.overview;

import com.htlimst.lieferrex.constraint.fieldMatch.FieldMatch;
import com.htlimst.lieferrex.controller.gericht.GerichtController;
import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OverveiwServiceImpl implements OverviewService {

    private SeitenaufrufeRepository seitenaufrufeRepository;
    private UmsatzRepository umsatzRepository;
    private GerichtRepository gerichtRepository;
    private GerichtBestellungRepository gerichtBestellungRepository;
    private BestellungRepository bestellungRepository;

    @Autowired
    public OverveiwServiceImpl(SeitenaufrufeRepository seitenaufrufeRepository, UmsatzRepository umsatzRepository, GerichtRepository gerichtRepository, GerichtBestellungRepository gerichtBestellungRepository, BestellungRepository bestellungRepository){
        this.seitenaufrufeRepository = seitenaufrufeRepository;
        this.umsatzRepository = umsatzRepository;
        this.gerichtRepository = gerichtRepository;
        this.gerichtBestellungRepository = gerichtBestellungRepository;
        this.bestellungRepository = bestellungRepository;
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

    @Override
    public List<Bestellung> getLatestBestellungen(long mandantId) {
        return this.bestellungRepository.getLatestThreeEntries(mandantId);
    }

    @Override
    public List<Gericht> anzahlGeakuft(long mandantId) {
        return gerichtRepository.getAllByMandant_Id(mandantId);
    }

    @Override
    public List<Umsatz> alleUmsaetzeByMandant(Mandant mandant) {
        return this.umsatzRepository.getAllByMandant(mandant);
    }

    @Override
    public boolean checkIfUmsatzImMonatVorhanden(Mandant mandant) {

        Umsatz umsatz = umsatzRepository.findFirstByMandantOrderByJahrDescMonatDesc(mandant);

        if(umsatz.getMonat() != LocalDateTime.now().getMonthValue()){
            umsatzRepository.save(new Umsatz(null, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear(), 0, mandant));
            return false;
        }
        return true;
    }


}
