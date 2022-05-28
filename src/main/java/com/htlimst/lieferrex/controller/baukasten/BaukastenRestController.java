package com.htlimst.lieferrex.controller.baukasten;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;
import com.htlimst.lieferrex.service.security.HttpSessionConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class BaukastenRestController {

    @Autowired
    MandantServiceImpl mandantServiceImpl;

    @Autowired
    FragmentServiceImpl fragmentServiceImpl;

    @Autowired
    FragmentTextServiceImpl fragmentTextServiceImpl;

    @Autowired
    AngestellterRepository angestellterRepository;
    
    @GetMapping("/baukasten/module/{position}")
    public String getModule(Model model, @PathVariable String position, @RequestParam String token){
        Optional<Angestellter> angestellter = angestellterRepository.findAngestellterByToken(token);
        if(angestellter.isPresent()){

            Mandant mandant = mandantServiceImpl.findMandantByAngestellterEmail(angestellter.get().getEmail()).get();
            Fragment fragment = fragmentServiceImpl.findFragmentByMandant_idAndPosition_name(mandant.getId(), position).get();


            model.addAttribute("content", fragment);
            return "baukasten/fragments/modules/" + fragment.getFragmenttype().getType();
        } else {
            return "Invalid User";
        }
    }

    @PostMapping("/baukasten/module/save")
    @CrossOrigin("*")
    public String saveModule(@RequestParam String data) {
        // Optional<Angestellter> angestellter = angestellterRepository.findAngestellterByToken(token);
        // if(angestellter.isPresent()){

        //     // TODO: Check Data, generate module, send success

        //     return "";
        // } else {
        //     return "Invalid User";
        // }

        System.out.println(data);

        return "";
    }
}
