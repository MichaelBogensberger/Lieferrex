package com.htlimst.lieferrex.controller.baukasten;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.model.fragments.FragmentHeader;
import com.htlimst.lieferrex.model.fragments.FragmentImage;
import com.htlimst.lieferrex.model.fragments.FragmentText;
import com.htlimst.lieferrex.model.fragments.FragmentType;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.OeffnungszeitRepository;
import com.htlimst.lieferrex.repository.SeitenaufrufeRepository;
import com.htlimst.lieferrex.service.aboutus.AboutUsServiceImpl;
import com.htlimst.lieferrex.service.fragment.FragmentServiceImpl;
import com.htlimst.lieferrex.service.fragmentheader.FragmentHeaderServiceImpl;
import com.htlimst.lieferrex.service.fragmentimage.FragmentImageServiceImpl;
import com.htlimst.lieferrex.service.fragmenttext.FragmentTextServiceImpl;
import com.htlimst.lieferrex.service.fragmenttype.FragmentTypeServiceImpl;
import com.htlimst.lieferrex.service.gallery.GalleryServiceImpl;
import com.htlimst.lieferrex.service.layout.LayoutServiceImpl;
import com.htlimst.lieferrex.service.mandant.MandantServiceImpl;
import com.htlimst.lieferrex.service.position.PositionServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    OeffnungszeitRepository oeffnungszeitRepository;

    @Autowired
    AboutUsServiceImpl aboutUsServiceImpl;

    @Autowired
    GalleryServiceImpl galleryServiceImpl;

    @Autowired
    SeitenaufrufeRepository seitenaufrufeRepository;

    public void allFragments(List<Fragment> fragments, Model model, Mandant mandant) {
        for (Fragment fragment : fragments) {
            model.addAttribute(fragment.getPosition().getName(), fragment);

            ArrayList<Gericht> gerichte = new ArrayList<>();

            for (Gericht ger : mandant.getGerichte()) {
                if ( ger.getStatus() == 1) {
                    gerichte.add(ger);
                }
            }

            if (fragment.getFragmenttype().getType().equals("karte")) {
                model.addAttribute("gerichte", gerichte);
            } else if (fragment.getFragmenttype().getType().equals("kontakt")) {
                model.addAttribute("kontaktstrasse", mandant.getStrasse());
                model.addAttribute("kontaktnummer", mandant.getHausnummer());
                model.addAttribute("kontaktort", mandant.getOrt());
                model.addAttribute("kontaktplz", mandant.getPlz());
                model.addAttribute("kontaktmail", mandant.getEmail());
                model.addAttribute("kontakttelefon", mandant.getTelefonnummer());
            } else if (fragment.getFragmenttype().getType().equals("image")) {
                if (fragment.getFragmentimage().getImageBlob() != null) {
                    model.addAttribute(fragment.getPosition().getName() + "image",
                            new String((fragment.getFragmentimage().getImageBlob())));
                }
            } else if (fragment.getFragmenttype().getType().contains("header")) {
                if (fragment.getFragmentheader().getImageBlob() != null) {
                    model.addAttribute(fragment.getPosition().getName() + "image",
                            new String((fragment.getFragmentheader().getImageBlob())));
                }
            } else if (fragment.getFragmenttype().getType().contains("zeiten")) {
                model.addAttribute("zeiten", oeffnungszeitRepository.findOeffnungszeitByMandant(mandant));
            } else if (fragment.getFragmenttype().getType().contains("map")) {
                model.addAttribute("lat", mandant.getGeoPosition().getGeoLat());
                model.addAttribute("long", mandant.getGeoPosition().getGeoLng());
            }

            if (aboutUsServiceImpl.findAboutusByMandant(mandant).isPresent()) {
                model.addAttribute("AboutUs", true);
            }

            if (galleryServiceImpl.findGalleryByMandant(mandant).isPresent()) {
                model.addAttribute("Gallery", true);
            }
        }
    }

    @GetMapping("/restaurant/{restaurant}")
    public String showRestaurant(Model model, @PathVariable Long restaurant) {

        Mandant mandant = mandantServiceImpl.findMandantById(restaurant).get();
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        Optional<Seitenaufrufe> seitenaufrufe = seitenaufrufeRepository
                .getSeitenaufrufeByMandantAndJahrAndMonat(mandant, year, month);

        if (seitenaufrufe.isPresent()) {
            seitenaufrufe.get().setAufrufe(seitenaufrufe.get().getAufrufe() + 1);
            seitenaufrufeRepository.save(seitenaufrufe.get());
        } else {
            seitenaufrufeRepository.save(new Seitenaufrufe(null, month, year, 1, mandant));
        }

        model.addAttribute("layout", mandant.getLayout().getName());
        model.addAttribute("restaurantName", mandant.getFirmenname());
        model.addAttribute("color", mandant.getAkzentFarbe());
        model.addAttribute("restaurantID", mandant.getId());

        allFragments(fragments, model, mandant);

        return "baukasten/frame";
    }

    @GetMapping("/baukasten")
    public String showBaukasten(Model model, Authentication authentication) {

        Mandant mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName()).get();
        List<Fragment> fragments = fragmentServiceImpl.findFragmentByMandant_id(mandant.getId());

        model.addAttribute("layout", mandant.getLayout().getName());
        model.addAttribute("edit", true);
        model.addAttribute("restaurantName", mandant.getFirmenname());
        model.addAttribute("color", mandant.getAkzentFarbe());

        boolean headerExists = false;
        for (Fragment fragment : fragments) {
            if (fragment.getFragmentheader() != null) {
                headerExists = true;
            }
        }

        if (!headerExists) {
            String headerType = "";
            switch (mandant.getLayout().getName()) {
                case "layoutEINS":
                    headerType = "header-1";
                    break;
                case "layoutZWEI":
                    headerType = "header-2";
                    break;
                case "layoutDREI":
                    headerType = "header-3";
                    break;
                case "layoutVIER":
                    headerType = "header-4";
                    break;
                default:
                    break;
            }

            Fragment fragment = new Fragment(null,
                    positionServiceImpl.findPostitionByNameAndLayout("r1c1", mandant.getLayout()).get(),
                    mandant, fragmentTypeServiceImpl.findFragmentTypeByType(headerType).get(), null, null,
                    null);
            fragmentServiceImpl.save(fragment);

            FragmentHeader fragmentHeader = new FragmentHeader(null, "titel", "text", null, fragment);
            fragmentHeaderServiceImpl.save(fragmentHeader);
            fragment.setFragmentheader(fragmentHeader);
        }

        allFragments(fragmentServiceImpl.findFragmentByMandant_id(mandant.getId()), model, mandant);

        return "baukasten/frame";
    }

    @PostMapping("/baukasten/module/save")
    public String saveModule(Authentication authentication, Model model, @RequestParam String data,
            @RequestParam Optional<MultipartFile> image) throws IOException {

        Fragment fragment = null;
        Optional<Position> position = null;
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());
        if (mandant.isPresent()) {

            position = positionServiceImpl.findPostitionByNameAndLayout(result.get("position"),
                    mandant.get().getLayout());
            Optional<FragmentType> fragmenttype = fragmentTypeServiceImpl.findFragmentTypeByType(result.get("type"));

            if (position.isPresent() && fragmenttype.isPresent()) {

                // Fragment wether editing Header or adding new Fragment
                // -----------------------------------------------------
                if (!fragmenttype.get().getType().contains("header")) {
                    fragment = fragmentServiceImpl.save(
                            new Fragment(null, position.get(), mandant.get(), fragmenttype.get(), null, null,
                                    null));
                }
                // Fragment wether editing Header or adding new Fragment
                // -----------------------------------------------------

                // Switch Depending on FragmentType
                switch (fragmenttype.get().getType()) {
                    case "text":
                        FragmentText fragmentText = fragmentTextServiceImpl.save(
                                new FragmentText(null, result.get("title"), result.get("text"), fragment));

                        fragment.setFragmenttext(fragmentText);
                        fragmentServiceImpl.save(fragment);
                        break;

                    case "image":
                        FragmentImage fragmentImage = fragmentImageServiceImpl.save(
                                new FragmentImage(null, result.get("title"),
                                        Base64.getEncoder().encode(image.get().getBytes()), fragment));

                        model.addAttribute(fragment.getPosition().getName() + "image",
                                new String(fragmentImage.getImageBlob()));

                        fragment.setFragmentimage(fragmentImage);
                        fragmentServiceImpl.save(fragment);
                        break;

                    case "header-1":
                    case "header-2":
                    case "header-3":
                    case "header-4":

                        fragment = fragmentServiceImpl.findFragmentByMandant_idAndPosition_name(mandant.get().getId(),
                                position.get().getName()).get();
                        FragmentHeader fragmentHeader = fragmentHeaderServiceImpl
                                .findFragmentheaderByFragment_id(fragment.getId()).get();
                        fragmentHeader.setTitel(result.get("title"));
                        fragmentHeader.setText(result.get("text"));

                        if (image.isPresent()) {
                            fragmentHeader.setImageBlob(Base64.getEncoder().encode(image.get().getBytes()));
                            model.addAttribute(fragment.getPosition().getName() + "image",
                                    new String(fragmentHeader.getImageBlob()));
                        }
                        fragmentHeaderServiceImpl.save(fragmentHeader);
                        break;

                    case "kontakt":
                        model.addAttribute("kontaktstrasse", mandant.get().getStrasse());
                        model.addAttribute("kontaktnummer", mandant.get().getHausnummer());
                        model.addAttribute("kontaktort", mandant.get().getOrt());
                        model.addAttribute("kontaktplz", mandant.get().getPlz());
                        model.addAttribute("kontaktmail", mandant.get().getEmail());
                        model.addAttribute("kontakttelefon", mandant.get().getTelefonnummer());
                        break;

                    case "zeiten":
                        model.addAttribute("zeiten", oeffnungszeitRepository.findOeffnungszeitByMandant(mandant.get()));
                        break;

                    case "karte":
                        model.addAttribute("gerichte", mandant.get().getGerichte());
                        break;

                    case "map":
                        model.addAttribute("lat", mandant.get().getGeoPosition().getGeoLat());
                        model.addAttribute("long", mandant.get().getGeoPosition().getGeoLng());
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
    public String deleteModule(Authentication authentication, Model model, @RequestParam String data)
            throws JsonMappingException, JsonProcessingException {
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());
        if (mandant.isPresent()) {
            Optional<Position> position = positionServiceImpl.findPostitionByNameAndLayout(result.get("position"),
                    mandant.get().getLayout());
            Optional<FragmentType> fragmenttype = fragmentTypeServiceImpl.findFragmentTypeByType(result.get("type"));

            if (position.isPresent() && fragmenttype.isPresent()) {

                Optional<Fragment> fragment = fragmentServiceImpl
                        .findFragmentByMandant_idAndPosition_name(mandant.get().getId(), position.get().getName());

                switch (fragmenttype.get().getType()) {
                    case "text":
                        Optional<FragmentText> fragmentText = fragmentTextServiceImpl
                                .findFragmenttextByFragment_id(fragment.get().getId());
                        fragmentTextServiceImpl.delete(fragmentText.get());
                        break;

                    case "image":
                        Optional<FragmentImage> fragmentImage = fragmentImageServiceImpl
                                .findFragmentimageByFragment_id(fragment.get().getId());
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
    public String updatePage(Authentication authentication, Model model, @RequestParam String data)
            throws JsonMappingException, JsonProcessingException {
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());
        if (mandant.isPresent()) {

            if (!result.get("color").isEmpty()) {
                mandant.get().setAkzentFarbe(result.get("color"));
            }

            if (!result.get("restaurantName").isEmpty()) {
                mandant.get().setFirmenname((result.get("restaurantName")));
            }

            Optional<Layout> layout = layoutServiceImpl.findLayoutByName(result.get("layout"));
            if (layout.isPresent()) {
                mandant.get().setLayout(layout.get());
                Optional<Fragment> fragment = fragmentServiceImpl
                        .findFragmentByMandant_idAndPosition_name(mandant.get().getId(), "r1c1");
                fragment.get().setFragmenttype(
                        fragmentTypeServiceImpl.findFragmentTypeByType("header-" + layout.get().getId()).get());
                fragmentServiceImpl.save(fragment.get());
            }

            mandantServiceImpl.save(mandant.get());

            if (layout.isPresent()) {
                mandant.get().getFragmente().stream().forEach(item -> {
                    if (item.getFragmenttype().getType().equals("text")) {
                        fragmentTextServiceImpl.delete(item.getFragmenttext());
                    }
                    if (item.getFragmenttype().getType().equals("image")) {
                        fragmentImageServiceImpl.delete(item.getFragmentimage());
                    }

                    if (!item.getFragmenttype().getType().contains("header")) {
                        fragmentServiceImpl.delete(item);
                    }
                });
            }

        }

        return showBaukasten(model, authentication);
    }

    @PostMapping("/baukasten/site/add")
    public String addSubPage(Authentication authentication, Model model, @RequestParam String data,
            @RequestParam List<MultipartFile> image) throws IOException {
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());

        switch (result.get("type")) {
            case "AboutUs":
                aboutUsServiceImpl.save(new AboutUs(null, result.get("title"), result.get("textOne"),
                        result.get("textTwo"), Base64.getEncoder().encode(image.get(0).getBytes()),
                        Base64.getEncoder().encode(image.get(1).getBytes()), mandant.get()));
                break;
            case "Gallery":
                galleryServiceImpl.save(
                        new Gallery(null, result.get("title"), Base64.getEncoder().encode(image.get(0).getBytes()),
                                Base64.getEncoder().encode(image.get(1).getBytes()),
                                Base64.getEncoder().encode(image.get(2).getBytes()),
                                Base64.getEncoder().encode(image.get(3).getBytes()),
                                Base64.getEncoder().encode(image.get(4).getBytes()),
                                mandant.get()));
                break;

            default:
                break;
        }

        return showBaukasten(model, authentication);
    }

    @GetMapping("/restaurant/{restaurant}/{type}")
    public String showSubpage(Authentication authentication, Model model, @PathVariable Long restaurant,
            @PathVariable String type) throws IOException {
        Optional<Mandant> mandant = mandantServiceImpl.findMandantById(restaurant);

        model.addAttribute("restaurantName", mandant.get().getFirmenname());
        model.addAttribute("color", mandant.get().getAkzentFarbe());
        model.addAttribute("backLink", "../");

        switch (type) {
            case "AboutUs":
                AboutUs aboutUs = mandant.get().getAboutus();
                model.addAttribute("layout", "AboutUs");
                model.addAttribute("title", aboutUs.getTitle());
                model.addAttribute("textOne", aboutUs.getTextOne());
                model.addAttribute("textTwo", aboutUs.getTextTwo());
                model.addAttribute("imageOne", new String(aboutUs.getImageBlobOne()));
                model.addAttribute("imageTwo", new String(aboutUs.getImageBlobTwo()));

                if (galleryServiceImpl.findGalleryByMandant(mandant.get()).isPresent()) {
                    model.addAttribute("Gallery", true);
                    model.addAttribute("GalleryLink", "../Gallery/");
                }

                break;

            case "Gallery":
                Gallery gallery = mandant.get().getGallery();
                model.addAttribute("layout", "Gallery");
                model.addAttribute("imageOne", new String(gallery.getImageBlobOne()));
                model.addAttribute("imageTwo", new String(gallery.getImageBlobTwo()));
                model.addAttribute("imageThree", new String(gallery.getImageBlobThree()));
                model.addAttribute("imageFour", new String(gallery.getImageBlobFour()));
                model.addAttribute("imageFive", new String(gallery.getImageBlobFive()));

                if (aboutUsServiceImpl.findAboutusByMandant(mandant.get()).isPresent()) {
                    model.addAttribute("AboutUs", true);
                    model.addAttribute("AboutUsLink", "../AboutUs/");
                }

                break;

            default:
                break;
        }

        return "baukasten/frame";
    }

    @PostMapping("/baukasten/site/delete")
    public String removeSubPage(Authentication authentication, Model model, @RequestParam String data)
            throws IOException {
        HashMap<String, String> result = new ObjectMapper().readValue(data, HashMap.class);

        Optional<Mandant> mandant = mandantServiceImpl.findMandantByAngestellterEmail(authentication.getName());

        switch (result.get("type")) {
            case "AboutUs":
                aboutUsServiceImpl.delete(aboutUsServiceImpl.findAboutusByMandant(mandant.get()).get());
                break;

            case "Gallery":
                galleryServiceImpl.delete(galleryServiceImpl.findGalleryByMandant(mandant.get()).get());
                break;

            default:
                break;
        }

        return showBaukasten(model, authentication);
    }

}
