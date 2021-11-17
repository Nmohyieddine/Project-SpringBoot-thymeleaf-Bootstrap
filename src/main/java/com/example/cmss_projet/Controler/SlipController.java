package com.example.cmss_projet.Controler;

import com.example.cmss_projet.Repositories.ContractedRepositorie;
import com.example.cmss_projet.Repositories.SlipRepositorie;
import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Contracted;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class SlipController {

    @Autowired
    private SlipRepositorie slipRepositorie;

    @Autowired
    private ContractedRepositorie contractedRepositorie;

    @Autowired
    private Services services;

    @GetMapping(path = "/Slip")
    public String Slip(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "6") int size) {

        Page<Slip> Slips = slipRepositorie.findAll(PageRequest.of(page, size));
        model.addAttribute("Slips", Slips.getContent());
        model.addAttribute("CurrentPageSlip", page);
        List<Contracted> contracteds=contractedRepositorie.findAll();
        model.addAttribute("contracteds",contracteds);


        model.addAttribute("pageNumber", new int[Slips.getTotalPages()]);


        return "Slip";

    }


    @PostMapping(path = "/saveSlip")
    public String saveSlip(Slip slip) {


        slipRepositorie.save(slip);

        //Contracted contracted=contractedRepositorie.findByContractedCode(slip.contractedCode);
        //contracted.getSlip().add(slip);



        return "redirect:/Slip";

    }
    @PostMapping(path = "/saveComplete")
    public String saveComplete(@RequestParam(name = "slipCodeComplete") Long slipcode,@RequestParam(name = "TotalAmountNet") Float totalamountnet){
        Slip slip=slipRepositorie.findBySlipCode(slipcode);
        slip.setTotalAmountNet(totalamountnet);
        slipRepositorie.save(slip);

        return "redirect:/SlipVentilation";
    }


    @GetMapping(path = "/SlipVentiler")
    public String SlipVentiler(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "6") int size) {
        Pageable pagination = PageRequest.of(page, size);
        Page<Slip> Slips = slipRepositorie.findAllByStatus(1, pagination);
        model.addAttribute("SlipsVentiler", Slips.getContent());
        model.addAttribute("CurrentPageSlipVentiler", page);

        model.addAttribute("pageNumberVentiler", new int[Slips.getTotalPages()]);


        return "SlipVentiler";


    }

    @GetMapping(path = "/PaiementSlip")
    public String PaiementSlip(Model model,
                               @RequestParam(name = "page", defaultValue = "0")int page,
                               @RequestParam(name = "size",defaultValue = "4")int size,
                               @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Pageable pageble=PageRequest.of(page, size);
        Page<Slip> slipsPaiement=services.cherche(keyword,pageble);

        List<Contracted> contracteds=contractedRepositorie.findAll();
        model.addAttribute("conventionne",contracteds);


        model.addAttribute("SlipsPaiement",slipsPaiement.getContent());
        model.addAttribute("currentpageslipPaiement",page);
        model.addAttribute("pageslipPaiement",new int[slipsPaiement.getTotalPages()] );




        return "SlipPaiement";

    }


    @PostMapping(path = "/Payer")
    public String Payer(@RequestParam(value = "slipPaiementcode",defaultValue = "[]") List<Long> slipPaiement){
            services.changestatusSlip(slipPaiement);

            return "redirect:/PaiementSlip";

    }



    @GetMapping(path = "/PayedSlip")
    public String PayedSlip(Model model,
                               @RequestParam(name = "page", defaultValue = "0")int page,
                               @RequestParam(name = "size",defaultValue = "4")int size){

        Page<Slip> slipsPayed=slipRepositorie.findAllByStatusAndStatusPaiement(1,1,PageRequest.of(page, size));

        model.addAttribute("SlipsPayed",slipsPayed.getContent());

        model.addAttribute("currentpageslipPayed",page);

        model.addAttribute("pageslipPayed",new int[slipsPayed.getTotalPages()] );


        return "SlipsPayed";

    }

    @GetMapping(path = "/DeleteSlip")
    public String DeleteSlip( @RequestParam(value = "id") Long id,@RequestParam(value = "page",defaultValue = "0") int page){
        slipRepositorie.deleteById(id);

        return "redirect:/Slip";

    }
    @RequestMapping(value = "/EditSlip")
    public String EditSlip(Slip S){
        Slip slip=slipRepositorie.findBySlipCode(S.slipCode);
        slip.setContractedCode(S.contractedCode);
        slip.setReceptionDate(S.ReceptionDate);
        slip.setMonthSlip(S.MonthSlip);
        slip.setYearSlip(S.YearSlip);
        slip.setTotalAmount(S.TotalAmount);
        slip.setChangeDate(S.ChangeDate);
        slipRepositorie.save(slip);
        return "redirect:/Slip";




    }





}
