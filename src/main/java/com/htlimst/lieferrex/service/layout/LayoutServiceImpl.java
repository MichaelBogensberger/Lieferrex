package com.htlimst.lieferrex.service.layout;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlimst.lieferrex.model.Layout;
import com.htlimst.lieferrex.repository.LayoutRepository;

@Service
public class LayoutServiceImpl implements LayoutService {

    @Autowired
    LayoutRepository layoutRepository;

    @Override
    public Optional<Layout> findLayoutByName(String name) {
        return layoutRepository.findLayoutByName(name);
    }
    
}
