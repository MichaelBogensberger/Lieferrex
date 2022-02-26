package com.htlimst.lieferrex.service.security;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import com.htlimst.lieferrex.model.Rolle;

@Builder
@Getter
public class UserPojo {

    private String benutzername;
    private String passwort;
    private Collection<Rolle> rollen;

}
