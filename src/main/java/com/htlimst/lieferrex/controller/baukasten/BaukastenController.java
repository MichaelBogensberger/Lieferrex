package com.htlimst.lieferrex.controller.baukasten;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Fragment;
import com.htlimst.lieferrex.model.Gericht;
import com.htlimst.lieferrex.model.Layout;
import com.htlimst.lieferrex.model.Mandant;
import com.htlimst.lieferrex.model.Position;
import com.htlimst.lieferrex.model.fragments.FragmentHeader;
import com.htlimst.lieferrex.model.fragments.FragmentImage;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.model.fragments.FragmentType;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.PositionRepository;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmentheader.FragmentHeaderServiceImpl;
import com.htlimst.lieferrex.service.fragmentimage.FragmentImageServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.fragmenttype.FragmentTypeServiceImpl;
import com.htlimst.lieferrex.service.layout.LayoutServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;
import com.htlimst.lieferrex.service.position.PositionServiceImpl;

import com.htlimst.lieferrex.service.Util;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BaukastenController {

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

    @Autowired
    FragmentImageServiceImpl fragmentImageServiceImpl;

    @Autowired
    FragmentHeaderServiceImpl fragmentHeaderServiceImpl;

    @Autowired
    LayoutServiceImpl layoutServiceImpl;

    // TODO: Make model an Object, consistency

    @GetMapping("/restaurant/{restaurant}")
    public String showRestaurant(Model model, @PathVariable String restaurant){

        // TODO: Error-Handling

        Mandant mandant = mandantServiceImpl.findMandantByFirmenname(restaurant).get();
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        // Layout des Mandanten der VIEW uebergeben
        model.addAttribute("layout", mandant.getLayout().getName());

        for (Fragment fragment : fragments) {
            model.addAttribute(fragment.getPosition().getName(), fragment);

            // Datenermittlung bei Ausgabe von Karte, Kontakt, etc.
            if (fragment.getFragmenttype().getType().equals("karte")) {
                model.addAttribute("gerichte", mandant.getGerichte());
            } else if(fragment.getFragmenttype().getType().equals("kontakt")){
                // TODO: Kontakte des Mandanten ausgeben
                model.addAttribute("kontakt", "getKontakt");
            }
        }
                
        return "baukasten/frame";
    }

    @GetMapping("/baukasten")
    public String showBaukasten(Model model, Authentication authentication){

        Mandant mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName()).get();

        model.addAttribute("layout", mandant.getLayout().getName());
        model.addAttribute("edit", true);
        model.addAttribute("restaurantName", mandant.getFirmenname());

        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());
        model.addAttribute("layout", mandant.getLayout().getName());

        for (Fragment fragment : fragments) {
            model.addAttribute(fragment.getPosition().getName(), fragment);

            if (fragment.getFragmenttype().getType().equals("karte")) {
                model.addAttribute("gerichte", mandant.getGerichte());
            } else if(fragment.getFragmenttype().getType().equals("kontakt")){
                // TODO: Kontakte des Mandanten ausgeben
                model.addAttribute("kontakt", "getKontakt");
            }
        }

        return "baukasten/frame";
    }

    @GetMapping("/baukasten/module/{position}")
    public String getModule(Authentication authentication, Model model, @PathVariable String position, @RequestParam String token){

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());
        Fragment fragment = fragmentServiceImpl.findFragmentByMandant_idAndPosition_name(mandant.get().getId(), position).get();

        model.addAttribute("content", fragment);
        return "baukasten/fragments/modules/" + fragment.getFragmenttype().getType();
    }

    @PostMapping("/baukasten/module/save")
    public String saveModule(Authentication authentication, Model model, @RequestParam String data, @RequestParam Optional<MultipartFile> image) throws IOException {
        
        Fragment fragment = null;
        Optional<Position> position = null;
        String imageName;
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());
        if(mandant.isPresent()){

            position = positionServiceImpl.findPostitionByNameAndLayout(result.get("position"), mandant.get().getLayout());
            Optional<FragmentType> fragmenttype = fragmentTypeServiceImpl.findFragmentTypeByType(result.get("type"));

            if(position.isPresent() && fragmenttype.isPresent()){

                // Fragment wether editing Header or adding new Fragment -----------------------------------------------------
                if(!fragmenttype.get().getType().contains("header")){
                    fragment = fragmentServiceImpl.save(
                        new Fragment(null, position.get(), mandant.get(), fragmenttype.get(), null, null, null, null));    
                }
                // Fragment wether editing Header or adding new Fragment -----------------------------------------------------


                // Switch Depending on FragmentType
                switch (fragmenttype.get().getType()) {
                    case "text":
                        FragmentText fragmentText = fragmentTextServiceImpl.save(
                            new FragmentText(null, result.get("title"), result.get("text"), "null", fragment));
                            
                        fragment.setFragmenttext(fragmentText);
                        fragmentServiceImpl.save(fragment);
                        break;
                        
                    case "image":
                        imageName = mandant.get().getFirmenname() + "-" + mandant.get().getId() + "-" + position.get().getName() + "." + image.get().getOriginalFilename().substring(image.get().getOriginalFilename().lastIndexOf(".") + 1);
                        Util.saveFile("src/main/resources/static/images/", imageName, image.get());
                        
                        FragmentImage fragmentImage = fragmentImageServiceImpl.save(
                            new FragmentImage(null, result.get("title"), imageName, fragment)
                            );
                            
                        fragment.setFragmentimage(fragmentImage);
                        fragmentServiceImpl.save(fragment);
                        break;

                    case "header-1":

                        fragment = fragmentServiceImpl.findFragmentByMandant_idAndPosition_name(mandant.get().getId(), position.get().getName()).get();
                        FragmentHeader fragmentHeader = fragmentHeaderServiceImpl.findFragmentheaderByFragment_id(fragment.getId()).get();
                        fragmentHeader.setTitel(result.get("title"));
                        fragmentHeader.setText(result.get("text"));
                        
                        if(image.isPresent()) {
                            imageName = mandant.get().getFirmenname() + "-" + mandant.get().getId() + "-" + position.get().getName() + "." + image.get().getOriginalFilename().substring(image.get().getOriginalFilename().lastIndexOf(".") + 1);
                            Util.saveFile("src/main/resources/static/images/", imageName, image.get());
                            fragmentHeader.setImage(imageName);
                        }
                        fragmentHeaderServiceImpl.save(fragmentHeader);
                        break;

                    default:
                        break;
                }
            }

        }

            // Returning new Fragment
            model.addAttribute("content", fragment);
            model.addAttribute("edit", true);
            model.addAttribute("position", position.get().getName());
            return "baukasten/fragments/modules/" + fragment.getFragmenttype().getType();
    }

    @PostMapping("baukasten/module/delete")
    public String deleteModule(Authentication authentication, Model model, @RequestParam String data) throws JsonMappingException, JsonProcessingException {
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());
        if(mandant.isPresent()) {
            Optional<Position> position = positionServiceImpl.findPostitionByNameAndLayout(result.get("position"), mandant.get().getLayout());
            Optional<FragmentType> fragmenttype = fragmentTypeServiceImpl.findFragmentTypeByType(result.get("type"));

            if(position.isPresent() && fragmenttype.isPresent()) {

                Optional<Fragment> fragment = fragmentServiceImpl.findFragmentByMandant_idAndPosition_name(mandant.get().getId(), position.get().getName());

                switch (fragmenttype.get().getType()) {
                    case "text":
                        Optional<FragmentText> fragmentText = fragmentTextServiceImpl.findFragmenttextByFragment_id(fragment.get().getId());
                        fragmentTextServiceImpl.delete(fragmentText.get());
                        break;
                    
                    case "image":
                        Optional<FragmentImage> fragmentImage = fragmentImageServiceImpl.findFragmentimageByFragment_id(fragment.get().getId());
                        fragmentImageServiceImpl.delete(fragmentImage.get());
                        break;
                }

                fragmentServiceImpl.delete(fragment.get());
                model.addAttribute("position", position.get().getName());
                return "baukasten/fragments/modules/add";
            }
        }

        return "";
    }

    @PostMapping("/baukasten/update")
    public String updatePage(Authentication authentication, Model model, @RequestParam String data) throws JsonMappingException, JsonProcessingException {
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);
        
        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());
        if(mandant.isPresent()) {
            
            if(!result.get("color").isEmpty()){
                mandant.get().setAkzentFarbe(result.get("color"));
            }

            if(!result.get("restaurantName").isEmpty()){
                mandant.get().setFirmenname((result.get("restaurantName")));
            }

            Optional<Layout> layout = layoutServiceImpl.findLayoutByName(result.get("layout"));
            if(layout.isPresent()){
                mandant.get().setLayout(layout.get());
            }

            mandantServiceImpl.save(mandant.get());
            
            if(layout.isPresent()){
                mandant.get().getFragmente().stream().forEach(item -> {
                    if(item.getFragmenttype().getType().equals("text")) {fragmentTextServiceImpl.delete(item.getFragmenttext());}
                    if(item.getFragmenttype().getType().equals("image")) {fragmentImageServiceImpl.delete(item.getFragmentimage());}
                    
                    if(!item.getFragmenttype().getType().contains("header")) {
                        fragmentServiceImpl.delete(item);
                    }
                });
                
                System.out.println("Done");
            }

        }

        return showBaukasten(model, authentication);
    }

}
