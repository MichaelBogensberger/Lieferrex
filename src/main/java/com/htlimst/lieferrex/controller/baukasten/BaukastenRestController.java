package com.htlimst.lieferrex.controller.baukasten;

import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaukastenRestController {

    @Autowired
    MandantServiceImpl mandantServiceImpl;

    @Autowired
    FragmentServiceImpl fragmentServiceImpl;

    @Autowired
    FragmentTextServiceImpl fragmentTextServiceImpl;
    
    @GetMapping("/baukasten/module/{position}")
    public String getModule(Model model, Authentication authentication, @PathVariable String position){
        Mandant mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName()).get();        
        Fragment fragment = fragmentServiceImpl.findFragmentByMandant_idAndPosition_name(mandant.getId(), position).get();

        model.addAttribute("content", fragment);

        return "baukasten/fragments/modules/" + fragment.getFragmenttype().getType();
    }
}
