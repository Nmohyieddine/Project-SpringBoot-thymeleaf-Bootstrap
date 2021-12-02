package com.example.cmss_projet.Controler;

import com.example.cmss_projet.Repositories.ContractedRepositorie;
import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Contracted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ContractedController {

    @Autowired
    private ContractedRepositorie contractedRepositorie ;

    @Autowired
    private Services services;



    @GetMapping(path = "/Contracted")
    public String Contracted(Model model
                            ,@RequestParam(name = "page",defaultValue = "0")int page
                            ,@RequestParam(name = "size",defaultValue = "5") int size)
                            {
        //List<Contracted> contracteds=contractedRepositorie.findAll();

        Page<Contracted> contracteds=contractedRepositorie.findAll(PageRequest.of(page,size));


        model.addAttribute("contracteds",contracteds.getContent());
        model.addAttribute("pagecontracted",new int[contracteds.getTotalPages()]);
        model.addAttribute("currentpage",page);

        return "contracted";

    }



    @PostMapping(path = "/save")
    public String saveContracted(  Contracted savecontracted ){

       contractedRepositorie.save(savecontracted);

        return "redirect:/Contracted";

    }

    @GetMapping(path = "/findOneContracted")
    @ResponseBody
    public Optional<Contracted> findOne(Long id){

        return contractedRepositorie.findById(id);


    }

    @GetMapping(path = "/DeleteContracted")
    public String DeleteContracted( @RequestParam(value = "id") Long id){
        contractedRepositorie.deleteById(id);
        return "redirect:/Contracted";

    }

    @RequestMapping(value = "EditContracted")
    public String EditContracted(Contracted contracted){
        Optional<Contracted> cont=contractedRepositorie.findById(contracted.getId());
        cont.get().setDenomination(contracted.getDenomination());
        cont.get().setContractedCode(contracted.getContractedCode());
        cont.get().setCity(contracted.getCity());
        cont.get().setMail(contracted.getMail());
        cont.get().setSpeciality(contracted.getSpeciality());
        cont.get().setPhoneNumber(contracted.getPhoneNumber());
        cont.get().setDurationToPay(contracted.getDurationToPay());
        contractedRepositorie.save(cont.get());
        return "redirect:/Contracted";

    }




}
