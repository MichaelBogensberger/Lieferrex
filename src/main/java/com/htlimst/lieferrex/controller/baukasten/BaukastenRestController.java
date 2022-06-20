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
import org.springframework.web.multipart.MultipartFile;

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
    
    

    // @PostMapping("/baukasten/module/save")
    // @CrossOrigin("*")
    // public String saveModule(@RequestParam String data, @RequestParam(required = false) MultipartFile image) throws JsonMappingException, JsonProcessingException {
        
    //     HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

    //     Optional<Angestellter> angestellter = angestellterRepository.findAngestellterByToken(result.get("token"));
    //     if(angestellter.isPresent()){

    //         Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(angestellter.get().getEmail());
    //         if(mandant.isPresent()){

    //             Optional<Position> position = positionServiceImpl.findPostitionByNameAndLayout(result.get("position"), mandant.get().getLayout());
    //             Optional<FragmentType> fragmenttype = fragmentTypeServiceImpl.findFragmentTypeByType(result.get("type"));

    //             if(position.isPresent() && fragmenttype.isPresent()){
    //                 Fragment fragment = fragmentServiceImpl.save(new Fragment(null, 
    //                                                                             position.get(), 
    //                                                                             mandant.get(), 
    //                                                                             fragmenttype.get(), 
    //                                                                             null, 
    //                                                                             null, 
    //                                                                             null,
    //                                                                             null));

    //                 System.out.println(fragmenttype.get().getType());
    //                 switch (fragmenttype.get().getType()) {
    //                     case "text":
    //                         FragmentText fragmentText = fragmentTextServiceImpl.save(
    //                             new FragmentText(null, 
    //                                 result.get("title"), 
    //                                 result.get("text"), 
    //                                 "null", 
    //                                 fragment));
    //                         break;
    //                     case "image":
    //                         System.out.println(image.getOriginalFilename());
    //                         break;
    //                     default:
    //                         break;
    //                 }
    //             }

    //         }

    //         return "6";
    //     } else {
    //         return "Invalid User";
    //     }
    // }
}
