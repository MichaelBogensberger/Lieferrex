package com.htlimst.lieferrex.service.layout;

import java.util.Optional;

import com.htlimst.lieferrex.model.Layout;

public interface LayoutService {
    Optional<Layout> findLayoutByName(String name);
}
