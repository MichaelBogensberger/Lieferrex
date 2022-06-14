package com.htlimst.lieferrex.controller.baukasten;

import java.util.HashMap;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Position;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.model.fragments.FragmentType;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.position.PositionServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.fragmenttype.FragmentTypeServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    PositionServiceImpl positionServiceImpl;

    @Autowired
    FragmentTypeServiceImpl fragmentTypeServiceImpl;
    
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
    public String saveModule(@RequestParam String data) throws JsonMappingException, JsonProcessingException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> result = objectMapper.readValue(data, HashMap.class);

        Optional<Angestellter> angestellter = angestellterRepository.findAngestellterByToken(result.get("token"));
        if(angestellter.isPresent()){
            Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(angestellter.get().getEmail());
            if(mandant.isPresent()){

                Optional<Position> position = positionServiceImpl.findPostitionByNameAndLayout(result.get("position"), mandant.get().getLayout());
                Optional<FragmentType> fragmenttype = fragmentTypeServiceImpl.findFragmentTypeByType(result.get("type"));
                if(position.isPresent() && fragmenttype.isPresent()){
                    // new FragmentText(null, "", "", "", null)
                    
                    Fragment fragment = fragmentServiceImpl.save(new Fragment(null, 
                                                                                position.get(), 
                                                                                mandant.get(), 
                                                                                fragmenttype.get(), 
                                                                                null, 
                                                                                null, 
                                                                                null));

                    System.out.println(fragment.toString());
                    if(fragmenttype.get().getType().equals("text")){
                        FragmentText fragmentText = fragmentTextServiceImpl.save(new FragmentText(null, 
                                                                                                    result.get("title"), 
                                                                                                    result.get("text"), 
                                                                                                    "null", 
                                                                                                    fragment));
                        System.out.println(fragmentText.toString());
                    }
                }

            }

            return "";
        } else {
            return "Invalid User";
        }
    }
}
