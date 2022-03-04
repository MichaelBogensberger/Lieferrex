package com.htlimst.lieferrex.security;

import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.lang.reflect.Array;


import java.security.Principal;
import java.util.Collection;


@Service
public class AccessValidation {

    @Autowired
    MandantRepository mandantRepository;

    @Autowired
    KundeRepository kundeRepository;


    public boolean isKunde(UserPrincipal principal) {
        String role = principal.getAuthorities().iterator().next().getAuthority();

        if(role.equals("ROLE_KUNDE")) {
            return true;
        } else {
            return false;
        }
    }






}
