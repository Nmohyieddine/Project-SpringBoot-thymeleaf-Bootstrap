package com.example.cmss_projet.Controler;

import com.example.cmss_projet.DTO.SlipTdo;
import com.example.cmss_projet.Repositories.ContractedRepositorie;
import com.example.cmss_projet.Repositories.RegieRepositorie;
import com.example.cmss_projet.Repositories.SlipRepositorie;
import com.example.cmss_projet.entities.Contracted;
import com.example.cmss_projet.entities.Regie;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class SlipVentilationControler {

    @Autowired
    private SlipRepositorie slipVentilationRepositorie;
    @Autowired
    private ContractedRepositorie contractedRepositorie;

    @Autowired
    private  SlipRepositorie slipRepositorie;



    @GetMapping(path = "/SlipVentilation")
    public String SlipVentilation(Model model,
                                  @RequestParam(name = "page" , defaultValue = "0") int page,
                                  @RequestParam(name = "size" , defaultValue = "3") int size){
        Pageable pageable=PageRequest.of(page,size);

        List<Contracted> contracteds=contractedRepositorie.findAll();
        model.addAttribute("conventionne",contracteds);



        Page<Slip> slipsVentilation = slipVentilationRepositorie.findAllByStatus(0,pageable);
        model.addAttribute("slipsVentilation", slipsVentilation.getContent());
        model.addAttribute("CurrentPageSlipVentilation",page);
        model.addAttribute("pageNumber", new int[slipsVentilation.getTotalPages()]);

        return "SlipVentilation";

    }

    @GetMapping(path = "/SlipBankCheck")
    public String SlipBankcheck(@ModelAttribute("slipPaiementcode") final ArrayList<Long> slipPaiement
            , Model model
            ,final RedirectAttributes redirectAttributes
            ){



        List<Slip> Slips= slipRepositorie.findBySlipCodeIn(slipPaiement);
        model.addAttribute("slipbankcheck",Slips);

        redirectAttributes.addFlashAttribute("redirectSlipbankcheck", Slips);
    /*
        int size=slipPaiement.size();

        SlipTdo slipTdo=new SlipTdo();

        for (Long slipcode:slipPaiement){

            slipTdo.addSlip(slipRepositorie.findBySlipCode(slipcode));

        }

        model.addAttribute("formSlip",slipTdo);
    */


        return "SlipBankcheck";


    }



}
