package com.htlimst.lieferrex.service.gericht;

import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.repository.GerichtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerichtServiceImpl implements GerichtService{

    private GerichtRepository gerichtRepo;

    public GerichtServiceImpl(GerichtRepository gerichtRepo){
        this.gerichtRepo =gerichtRepo;
    }

    @Override
    public Gericht gerichtEinfuegen(Gericht gericht) {
        return gerichtRepo.save(gericht);
    }

    @Override
    public Gericht gerichtAusgebenById(Long id) {
        return null;
    }

    @Override
    public Gericht gerichtUpdate(Long id) {
        return null;
    }

    @Override
    public void gerichtLoeschen(Long id) {
        boolean exists = gerichtRepo.existsById(id);
        if(!exists){
            throw new IllegalStateException("Gericht mit ID" + id + "nicht gefunden");
        }
        gerichtRepo.deleteById(id);
    }

    @Override
    public List<Gericht> gerichtAusgabe() {
        return gerichtRepo.findAll();
    }
}