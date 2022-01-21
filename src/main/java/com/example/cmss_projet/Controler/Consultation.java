package com.example.cmss_projet.Controler;

import com.example.cmss_projet.DTO.ContractedDTO;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
                       @RequestParam(name = "size", defaultValue = "50") int size) {

        Page<Slip> Slips = slipRepositorie.findAll(PageRequest.of(page, size));
        model.addAttribute("Slips", Slips.getContent());
        model.addAttribute("CurrentPageSlip", page);
        List<Contracted> contracteds=contractedRepositorie.findAll();

        model.addAttribute("contracteds",contracteds);

        //je pense pas qu'on va utiliser la pagination
        model.addAttribute("pageNumber", new int[Slips.getTotalPages()]);
        model.addAttribute("contractedConsultation",new ContractedDTO());



        return "CEOConsultation";

    }


    @PostMapping(value = "/ConsultationSearch")
    public String Consulter(@ModelAttribute ContractedDTO contractedConsultation, Model model){


        if(contractedConsultation.ContractedCode.equals("false")){

            if(contractedConsultation.getDateDebut()==null || contractedConsultation.getDateFin()==null){

                List<Slip> AllBodereauxPayer=slipRepositorie.findAllByStatusPaiement(1);
                List<Slip> AllBordereauNonPayer=slipRepositorie.findAllByStatusPaiement(0);
                List<Slip> AllBordereauVentiler=slipRepositorie.findByStatus(1);
                List<Slip> AllBordereauNonVentiler=slipRepositorie.findByStatus(0);

                model.addAttribute("BordereauxPayer", AllBodereauxPayer);
                model.addAttribute("BordereauxNonPayer",AllBordereauNonPayer);
                model.addAttribute("BordereauxVentiler",AllBordereauVentiler);
                model.addAttribute("BordereauxNonVentiler",AllBordereauNonVentiler);



            }else {

                List<Slip> AllBodereauxPayer=slipRepositorie.SlipPaiementwithDateAll(1,contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());
                List<Slip> AllBordereauNonPayer=slipRepositorie.SlipPaiementwithDateAll(0,contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());
                List<Slip> AllBordereauVentiler=slipRepositorie.SlipVentilationWithDateAll(1,contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());
                List<Slip> AllBordereauNonVentiler=slipRepositorie.SlipVentilationWithDateAll(0,contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());

                model.addAttribute("BordereauxPayer", AllBodereauxPayer);
                model.addAttribute("BordereauxNonPayer",AllBordereauNonPayer);
                model.addAttribute("BordereauxVentiler",AllBordereauVentiler);
                model.addAttribute("BordereauxNonVentiler",AllBordereauNonVentiler);



            }





        }else{

            if(contractedConsultation.getDateDebut()==null || contractedConsultation.getDateFin()==null) {
                List<Slip> ListBordereauxPayer=slipRepositorie.findByStatusPaiementAndContractedCode(1,contractedConsultation.getContractedCode());
                List<Slip> ListBordereauxNonPayer=slipRepositorie.findByStatusPaiementAndContractedCode(0,contractedConsultation.getContractedCode());
                List<Slip> ListBordereauxVentiler=slipRepositorie.findByStatusAndContractedCode(1,contractedConsultation.getContractedCode());
                List<Slip> ListBordereauxNonVentiler=slipRepositorie.findByStatusAndContractedCode(0,contractedConsultation.getContractedCode());


                model.addAttribute("BordereauxPayer", ListBordereauxPayer);
                model.addAttribute("BordereauxNonPayer",ListBordereauxNonPayer);
                model.addAttribute("BordereauxVentiler",ListBordereauxVentiler);
                model.addAttribute("BordereauxNonVentiler",ListBordereauxNonVentiler);

            }else{
                List<Slip> ListBordereauxPayer =slipRepositorie.SlipPaiementwithDate(1,contractedConsultation.getContractedCode(),contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());
                List<Slip> ListBordereauxNonPayer =slipRepositorie.SlipPaiementwithDate(0,contractedConsultation.getContractedCode(),contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());
                List<Slip> ListBordereauxVentiler =slipRepositorie.SlipVentilationWithDate(1,contractedConsultation.getContractedCode(),contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());
                List<Slip> ListBordereauxNonVentiler =slipRepositorie.SlipVentilationWithDate(0,contractedConsultation.getContractedCode(),contractedConsultation.getDateDebut(),contractedConsultation.getDateFin());

                model.addAttribute("BordereauxPayer", ListBordereauxPayer);
                model.addAttribute("BordereauxNonPayer",ListBordereauxNonPayer);
                model.addAttribute("BordereauxVentiler",ListBordereauxVentiler);
                model.addAttribute("BordereauxNonVentiler",ListBordereauxNonVentiler);

            }






        }
        model.addAttribute("contractedConsultation",contractedConsultation);
        List<Contracted> contracteds=contractedRepositorie.findAll();
        model.addAttribute("contracteds",contracteds);







        return "CEOConsultation";

    }


}
