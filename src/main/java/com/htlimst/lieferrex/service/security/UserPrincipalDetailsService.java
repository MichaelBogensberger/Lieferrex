package com.htlimst.lieferrex.service.security;

import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.model.Rolle;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.KundeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private KundeRepository kundeRepository;
    private AngestellterRepository angestellterRepository;

    @Autowired
    public UserPrincipalDetailsService(KundeRepository kundeRepository, AngestellterRepository angestellterRepository) {
        this.kundeRepository = kundeRepository;
        this.angestellterRepository = angestellterRepository;
    }

    //Lädt einen Kunden oder Angestellten per Email und gibt ihn dann als UserPrincipal zurück
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo user = findUserByEmail(username);

        if (user ==null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }

    //
    public UserPojo findUserByEmail(String email){
        UserPojo userPojo = null;
        Optional<Kunde> kunde = Optional.ofNullable(this.kundeRepository.findByEmail(email));
        Optional<Angestellter> angestellter = Optional.ofNullable(this.angestellterRepository.findByEmail(email));

        if (kunde.isPresent()){
            userPojo = UserPojo.builder().
                    benutzername(kunde.get().getEmail()).
                    passwort(kunde.get().getPasswort()).
                    rollen(Arrays.asList(new Rolle("ROLE_USER"))).
                    build();
        } else if (angestellter.isPresent()){
            userPojo = UserPojo.builder().
                    benutzername(angestellter.get().getEmail()).
                    passwort(angestellter.get().getPasswort()).
                    rollen(angestellter.get().getRolle()).
                    build();
        }

        return userPojo;
    }

}
