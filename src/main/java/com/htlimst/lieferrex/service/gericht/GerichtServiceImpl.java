package com.htlimst.lieferrex.service.gericht;

import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.repository.GerichtRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GerichtServiceImpl implements GerichtService{

    private GerichtRepository gerichtRepo;

    public GerichtServiceImpl(GerichtRepository gerichtRepo){
        this.gerichtRepo = gerichtRepo;
    }

    @Override
    public List<Gericht> getGerichte() {
        return gerichtRepo.findAll();
    }

    @Override
    public Optional<Gericht> getGerichtById(Long id) {
        return gerichtRepo.findById(id);
    }

    @Override
    public List<Gericht> getGerichtByStatus(Long mandantId) {
        return gerichtRepo.getGerichtByEnabled(mandantId);
    }

    @Override
    public List<Gericht> getGerichtByStatusZero(Long mandantId) {
        return gerichtRepo.getGerichtByEnabledWhereStatusZero(mandantId);
    }

    @Override
    public Gericht apiUpdateGericht(Long id, Gericht givenGericht) {
        Gericht foundGericht = gerichtRepo.findById(id).get();

        foundGericht.setName(givenGericht.getName());
        foundGericht.setBeschreibung(givenGericht.getBeschreibung());
        foundGericht.setPreis(givenGericht.getPreis());
        foundGericht.setPreisangebot(givenGericht.getPreisangebot());
        foundGericht.setStatus(givenGericht.getStatus());

        return gerichtRepo.save(foundGericht);
    }
    public void save(Gericht gericht){
        gerichtRepo.save(gericht);
    }

    @Override
    public void savingStatusGericht(Long id) {
        Optional<Gericht> foundGericht = gerichtRepo.findById(id);
        foundGericht.get().setStatus(1);
        gerichtRepo.save(foundGericht.get());
    }

    @Override
    public long numberDisabled() {
        return gerichtRepo.countDisabled();
    }

    @Override
    public long numberActive() {
        return gerichtRepo.countActive();
    }

    @Override
    public long numberAll() {
        return gerichtRepo.count();
    }


}