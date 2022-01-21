package com.example.cmss_projet.Service;

import com.example.cmss_projet.Repositories.BankcheckRepositorie;
import com.example.cmss_projet.Repositories.ContractedRepositorie;
import com.example.cmss_projet.Repositories.InvoiceRepositorie;
import com.example.cmss_projet.Repositories.SlipRepositorie;
import com.example.cmss_projet.entities.Bankcheck;
import com.example.cmss_projet.entities.Contracted;
import com.example.cmss_projet.entities.Invoice;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public  class Services {

    @Autowired
    private SlipRepositorie slipRepositorie;
    @Autowired
    private InvoiceRepositorie invoiceRepositorie;
    @Autowired
    private ContractedRepositorie contractedRepositorie;
    @Autowired
    private BankcheckRepositorie bankcheckRepositorie;

    public void changestatusSlip(Long id){
        Slip s=slipRepositorie.findBySlipCode(id);
        s.setStatus(1);
        slipRepositorie.save(s);

    }

    public void changestatusPaiementSlip(Long id ){
        Slip s=slipRepositorie.findBySlipCode(id);
        s.setStatusPaiement(1);
        slipRepositorie.save(s);

    }
    public void changestatusSlip(ArrayList<Long> slipsid){
        List<Slip> slip=slipRepositorie.findBySlipCodeIn(slipsid);
        for (Slip slip1 : slip){
            slip1.setStatusPaiement(1);
            slipRepositorie.save(slip1);
        }

    }

    public void changestatusSlipObject(List<Slip> slipList){

        for(Slip slip:slipList){

            changestatusPaiementSlip(slip.slipCode);

        }
    }
    public void changestatusInvoice(Long id){
        List<Invoice> invoices=invoiceRepositorie.findInvoiceBySlipCode(id);
        for (Invoice invoice : invoices) {
            invoice.setStatus(1);
            invoiceRepositorie.save(invoice);

        }

    }

    public Page<Slip> cherche(String keyword,int Status,int StatusPaiement ,Pageable pageable){
        Page<Slip> Listofcherche;
        if(keyword!=null){
            Listofcherche =slipRepositorie.chercher(keyword,Status,StatusPaiement,pageable);
        }
        else {
            Listofcherche=slipRepositorie.findAllByStatusAndStatusPaiement(Status,StatusPaiement,pageable);
        }

        return Listofcherche;


    }

    public Page<Slip> chercheAll(String keyword,Pageable pageable){
        Page<Slip> Listofcherche;
        if(keyword!=null){
            Listofcherche =slipRepositorie.chercherAll(keyword,pageable);
        }
        else {
            Listofcherche=slipRepositorie.findAll(pageable);
        }

        return Listofcherche;


    }
    public Page<Slip> chercheVentiler(String keyword,int status,Pageable pageable){
        Page<Slip> Listofcherche;
        if(keyword!=null){
            Listofcherche =slipRepositorie.chercherAllVentiler(keyword,status,pageable);
        }
        else {
            Listofcherche=slipRepositorie.findAllByStatus(status,pageable);
        }

        return Listofcherche;


    }


    public Page<Bankcheck> chercheAllBankchek(String keyword,Pageable pageable){
        Page<Bankcheck> Listofcherche;
        if(keyword!=null){
            int numero=Integer.parseInt(keyword);
            Listofcherche =bankcheckRepositorie.findByCheckNumber(numero,pageable);
        }
        else {
            Listofcherche=bankcheckRepositorie.findAll(pageable);
        }

        return Listofcherche;


    }
    public void updateDate(Long id){
        Optional<Slip> slip=slipRepositorie.findById(id);
        LocalDate localDate = LocalDate.now();
        slip.get().setSendContability(localDate);
        slipRepositorie.save(slip.get());

    }

    public void updateSlip(Slip EditSlip){

        Contracted contracted=contractedRepositorie.findByContractedCode(EditSlip.contractedCode);



    }

    public void updateContracted(Contracted Editcontracted){
        contractedRepositorie.save(Editcontracted);
    }


    public LocalDate addMonth(LocalDate date,Long mount) {
        LocalDate newDate=date.plusMonths(mount);

        return newDate;

    }

    public void bankcheckslip(Bankcheck bankcheck,List<Slip> slips){


        for(Slip s:slips){
            int numero=bankcheck.getCheckNumber();
            s.setNumerocheque(numero);
            slipRepositorie.save(s);
        }


    }

    public void deletInvoices(List<Invoice> Invoices){

        invoiceRepositorie.deleteAll(Invoices);
    }




}
