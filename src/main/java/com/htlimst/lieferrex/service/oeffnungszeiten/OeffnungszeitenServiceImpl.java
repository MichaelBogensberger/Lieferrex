package com.htlimst.lieferrex.service.oeffnungszeiten;

import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Oeffnungszeit;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.OeffnungszeitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OeffnungszeitenServiceImpl implements OeffnungszeitenService{
    private OeffnungszeitRepository oeffnungszeitRepository;
    private AngestellterRepository angestellterRepository;

    @Autowired
    public OeffnungszeitenServiceImpl(OeffnungszeitRepository oeffnungszeitRepository, AngestellterRepository angestellterRepository) {
        this.oeffnungszeitRepository = oeffnungszeitRepository;
        this.angestellterRepository = angestellterRepository;
    }

    @Override
    public List<Oeffnungszeit> alleOeffnungszeiten(Mandant mandant) {
        return oeffnungszeitRepository.findOeffnungszeitByMandant(mandant);
    }

    @Override
    public void saveOeffnungszeiten(List<Oeffnungszeit> oeffnungszeitList) {
        oeffnungszeitRepository.saveAll(oeffnungszeitList);
    }
}
