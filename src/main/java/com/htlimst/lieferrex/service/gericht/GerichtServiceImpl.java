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
    public List<Gericht> getGerichtByStatus() {
        return gerichtRepo.getGerichtByEnabled();
    }

    @Override
    public List<Gericht> getGerichtByStatusZero() {
        return gerichtRepo.getGerichtByEnabledWhereStatusZero();
    }
}