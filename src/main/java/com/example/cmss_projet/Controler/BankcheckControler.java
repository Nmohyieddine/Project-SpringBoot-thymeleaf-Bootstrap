package com.example.cmss_projet.Controler;

import com.example.cmss_projet.DTO.SlipTdo;
import com.example.cmss_projet.Repositories.BankcheckRepositorie;
import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Bankcheck;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.GeneratedValue;
import java.util.List;

@Controller
public class BankcheckControler {

    @Autowired
    private BankcheckRepositorie bankcheckRepositorie;
    @Autowired
    private Services services;




    @GetMapping(path = "/Bankcheck")
    public String BankCheck(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "6") int size ){

        Page<Bankcheck> bankchecks=bankcheckRepositorie.findAll(PageRequest.of(page, size));
        model.addAttribute("bankchecks",bankchecks.getContent());
        model.addAttribute("currentpagebankcheck",page);
        model.addAttribute("pagesbankcheck",new int[bankchecks.getTotalPages()]);

        return "Bankcheck";


    }

    /*
        @ModelAttribute SlipTdo form
     */

    @PostMapping(path = "/saveBankcheck")
    public String saveSlip(Bankcheck bankcheck, Model model) {




        bankcheckRepositorie.save(bankcheck);
        model.addAttribute("bankcheck",bankcheck);


        //services.bankcheckslip(bankcheck,form.getSlips());


        return "redirect:/Bankcheck";


    }
}
