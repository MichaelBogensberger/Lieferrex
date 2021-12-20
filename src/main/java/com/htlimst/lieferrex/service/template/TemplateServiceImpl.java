package com.htlimst.lieferrex.service.template;

import com.htlimst.lieferrex.model.Template;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.repository.TemplateRepository;
import com.htlimst.lieferrex.service.mandant.MandantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {
    
    @Autowired
    private TemplateRepository templateRepository;

    public Template getTemplateById(Long id){
        return templateRepository.findTemplateById(id).get();
    }

}
