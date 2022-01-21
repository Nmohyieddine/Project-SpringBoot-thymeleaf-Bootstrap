package com.example.cmss_projet.Controler;

import com.example.cmss_projet.DTO.SlipTdo;
import com.example.cmss_projet.Repositories.BankcheckRepositorie;
import com.example.cmss_projet.Repositories.ContractedRepositorie;
import com.example.cmss_projet.Repositories.RegieRepositorie;
import com.example.cmss_projet.Repositories.SlipRepositorie;
import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Bankcheck;
import com.example.cmss_projet.entities.Contracted;
import com.example.cmss_projet.entities.Regie;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes({"slipbankcheck"})
public class SlipVentilationControler {

    @Autowired
    private SlipRepositorie slipVentilationRepositorie;
    @Autowired
    private ContractedRepositorie contractedRepositorie;

    @Autowired
    private  SlipRepositorie slipRepositorie;


    @Autowired
    private BankcheckRepositorie bankcheckRepositorie;
    @Autowired
    private Services services;




    @GetMapping(path = "/SlipVentilation")
    public String SlipVentilation(Model model,
                                  @RequestParam(name = "page" , defaultValue = "0") int page,
                                  @RequestParam(name = "size" , defaultValue = "50") int size,
                                  @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Pageable pageable=PageRequest.of(page,size);

        List<Contracted> contracteds=contractedRepositorie.findAll();
        model.addAttribute("conventionne",contracteds);



        //Page<Slip> slipsVentilation = slipVentilationRepositorie.findAllByStatus(0,pageable);
        Page<Slip> slipsVentilation=services.cherche(keyword,0,0,pageable);

        model.addAttribute("slipsVentilation", slipsVentilation.getContent());
        model.addAttribute("CurrentPageSlipVentilation",page);
        model.addAttribute("pageNumber", new int[slipsVentilation.getTotalPages()]);

        return "SlipVentilation";

    }
    //Paiement
    @GetMapping(path = "/SlipBankCheck")
    public String SlipBankcheck(@RequestParam("slipPaiementcode") List<Long> slipPaiement,
            Bankcheck cheque, ModelMap model){



        List<Slip> Slips= slipRepositorie.findBySlipCodeIn(slipPaiement);
        bankcheckRepositorie.save(cheque);
        model.addAttribute("bankcheck",cheque);

        services.changestatusSlipObject(Slips);
        services.bankcheckslip(cheque,Slips);



        return "redirect:PaiementSlip";


    }



}
