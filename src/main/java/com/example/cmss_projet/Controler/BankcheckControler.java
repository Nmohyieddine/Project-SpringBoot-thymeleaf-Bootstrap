package com.example.cmss_projet.Controler;

import com.example.cmss_projet.DTO.BankchekDTO;
import com.example.cmss_projet.DTO.SlipTdo;
import com.example.cmss_projet.Repositories.BankcheckRepositorie;
import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Bankcheck;
import com.example.cmss_projet.entities.Contracted;
import com.example.cmss_projet.entities.Invoice;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@Controller

public class BankcheckControler {

    @Autowired
    private BankcheckRepositorie bankcheckRepositorie;

    @Autowired
    private Services services;





    @GetMapping(path = "/Bankcheck")
    public String BankCheck(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "50") int size) {

        Page<Bankcheck> bankchecks=bankcheckRepositorie.findAll(PageRequest.of(page, size));
        //Page<Bankcheck> bankchecks=services.chercheAllBankchek(keyword,PageRequest.of(page, size));
        model.addAttribute("bankchecks",bankchecks.getContent());
        model.addAttribute("currentpagebankcheck",page);
        model.addAttribute("pagesbankcheck",new int[bankchecks.getTotalPages()]);

        return "Bankcheck";


    }

    @RequestMapping(value = "EditCheck")

        public String EditCheck(BankchekDTO bankchekDTO){
         Optional<Bankcheck> bankcheck=bankcheckRepositorie.findById(bankchekDTO.checkid);
         bankcheck.get().setCheckDateRetirer(bankchekDTO.DateRetirerCheque);

         bankcheckRepositorie.save(bankcheck.get());

        return "redirect:/Bankcheck";

        }

    @RequestMapping(value = "CheckEdit")

    public String CheckEdit(BankchekDTO bankchekDTO){
        Optional<Bankcheck> bankcheck=bankcheckRepositorie.findById(bankchekDTO.checkid);

        bankcheck.get().setCheckDate(bankchekDTO.CheckDate);
        bankcheck.get().setCheckNumber(bankchekDTO.CheckNumber);
        bankcheck.get().setAmount(bankchekDTO.Amount);
        bankcheck.get().setCheckNumber(bankchekDTO.CheckNumber);
        bankcheck.get().setBank(bankchekDTO.Bank);

        bankcheckRepositorie.save(bankcheck.get());

        return "redirect:/Bankcheck";

    }



}
