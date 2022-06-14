package com.htlimst.lieferrex.service.position;

import com.htlimst.lieferrex.model.Layout;
import com.htlimst.lieferrex.model.Position;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    Optional<Position> findPostitionByNameAndLayout(String name, Layout layout);

}
