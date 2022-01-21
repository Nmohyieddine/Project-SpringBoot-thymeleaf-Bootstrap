package com.example.cmss_projet.Controler;

import com.example.cmss_projet.Repositories.InvoiceRepositorie;
import com.example.cmss_projet.Repositories.RegieRepositorie;
import com.example.cmss_projet.Repositories.SlipRepositorie;
import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Invoice;
import com.example.cmss_projet.entities.Regie;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class InvoiceController {


    @Autowired
    private InvoiceRepositorie invoiceRepositorie;
    @Autowired
    private SlipRepositorie slipVentilationRepositorie;
    @Autowired
    private Services services;

    @Autowired
    private RegieRepositorie regieRepositorie;

    @GetMapping(value = "/findOneSlip")
    public  String GetOne(Model model, Long id){
        Optional<Slip> SlipVentiler=slipVentilationRepositorie.findById(id);

        model.addAttribute("SlipVentiler",SlipVentiler);

        Slip slip=slipVentilationRepositorie.getById(id);
        model.addAttribute("ActualSlipVentilation", slip);

        List<Invoice> invoices=invoiceRepositorie.findInvoiceBySlipCode(id);
        model.addAttribute("invoices",invoices);

        List<Regie> Regies=regieRepositorie.findAll();
        model.addAttribute("regies",Regies);


        model.addAttribute("SumMontant",invoiceRepositorie.sumamount(id));

        model.addAttribute("montantBordereau",slip.TotalAmountNet);


        return "Ventilation";

    }


    @GetMapping(path = "/DeleteInvoice")
    public String DeleteInvoice(@RequestParam(value = "id") Long id){
        Optional<Invoice> inv=invoiceRepositorie.findById(id);
        invoiceRepositorie.deleteById(id);
        return "redirect:findOneSlip?id="+String.valueOf(inv.get().slipCode);


    }


    @PostMapping(path = "/saveInvoice")
    public String  saveContracted(Invoice invoice){


        invoiceRepositorie.save(invoice);


        return "redirect:findOneSlip?id="+String.valueOf(invoice.slipCode);

    }

    @GetMapping(path = "/Valider")
    public String Valider(Long id){

        services.changestatusSlip(id);
        services.changestatusInvoice(id);
        services.updateDate(id);


        return "redirect:/SlipVentiler";

    }
    @GetMapping(path = "/AllInvoiceVentilated")
    public String AllInvoiceVentilated(Model model,
                                       @RequestParam(name = "page" ,defaultValue = "0") int page,
                                       @RequestParam(name = "size" ,defaultValue = "50")int size){

        Pageable pageable= PageRequest.of(page,size);

        List<Slip> Slips=slipVentilationRepositorie.findAll();
        model.addAttribute("slips",Slips);
        Page<Invoice> invoices=invoiceRepositorie.findInvoiceByStatus(1,pageable);
        model.addAttribute("invoices",invoices.getContent());
        model.addAttribute("ActualPageInvoice",page);
        model.addAttribute("pageinvoice",new int[invoices.getTotalPages()]);



        return "Invoice";
    }

}
