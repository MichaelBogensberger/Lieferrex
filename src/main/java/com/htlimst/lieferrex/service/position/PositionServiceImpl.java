package com.htlimst.lieferrex.service.position;

import com.htlimst.lieferrex.model.Position;
import com.htlimst.lieferrex.repository.PositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService{
    
    @Autowired
    private PositionRepository repo;

    public Position getById(Long id) {
        return repo.findPositionById(id).get();
    }
}