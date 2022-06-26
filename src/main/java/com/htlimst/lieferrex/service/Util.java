package com.htlimst.lieferrex.service;

import com.htlimst.lieferrex.repository.KundeRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Collection;


@Service
public class Util {

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
