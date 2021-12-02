package com.example.cmss_projet.Controler;

import com.example.cmss_projet.Repositories.ContractedRepositorie;
import com.example.cmss_projet.Repositories.SlipRepositorie;
import com.example.cmss_projet.entities.Contracted;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Consultation {

    @Autowired
    SlipRepositorie slipRepositorie;

    @Autowired
    ContractedRepositorie contractedRepositorie;


    @GetMapping(path = "/Consultation")
    public String Slip(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "6") int size) {

        Page<Slip> Slips = slipRepositorie.findAll(PageRequest.of(page, size));
        model.addAttribute("Slips", Slips.getContent());
        model.addAttribute("CurrentPageSlip", page);
        List<Contracted> contracteds=contractedRepositorie.findAll();
        model.addAttribute("contracteds",contracteds);


        model.addAttribute("pageNumber", new int[Slips.getTotalPages()]);


        return "Consultation";

    }


    @PostMapping(value = "/Consultation")
    public String Consulter(@RequestParam(name = "contractedCode") String Contractedcode,Model model){

        List<Slip> ListBordereauxPayer =slipRepositorie.findByStatusAndContractedCode(1,Contractedcode);
        List<Slip> ListBordereauxNonPayer =slipRepositorie.findByStatusAndContractedCode(0,Contractedcode);
        List<Slip> ListBordereauxVentiler =slipRepositorie.findByStatusPaiementAndContractedCode(1,Contractedcode);
        List<Slip> ListBordereauxNonVentiler =slipRepositorie.findByStatusPaiementAndContractedCode(0,Contractedcode);

        model.addAttribute("BordereauxPayer", ListBordereauxPayer);
        model.addAttribute("BordereauxNonPayer",ListBordereauxNonPayer);
        model.addAttribute("BordereauxVentiler",ListBordereauxVentiler);
        model.addAttribute("BordereauxNonVentiler",ListBordereauxNonVentiler);

        return "redirect:/Consultation";

    }
}
