package com.htlimst.lieferrex.service.position;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Layout;
import com.htlimst.lieferrex.model.Position;
import com.htlimst.lieferrex.repository.FragmentRepository;
import com.htlimst.lieferrex.repository.PositionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public Optional<Position> findPostitionByNameAndLayout(String name, Layout layout) {
        return positionRepository.findByNameAndLayout(name, layout);
    }

}
