package com.example.cmss_projet.Service;

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

    public void changestatusSlip(Long id){
        Slip s=slipRepositorie.findById(id).get();
        s.setStatus(1);
        slipRepositorie.save(s);

    }
    public void changestatusSlip(List<Long> slipsid){
        List<Slip> slip=slipRepositorie.findBySlipCodeIn(slipsid);
        for (Slip slip1 : slip){
            slip1.setStatusPaiement(1);
            slipRepositorie.save(slip1);
        }

    }
    public void changestatusInvoice(Long id){
        List<Invoice> invoices=invoiceRepositorie.findInvoiceBySlipCode(id);
        for (Invoice invoice : invoices) {
            invoice.setStatus(1);
            invoiceRepositorie.save(invoice);

        }

    }

    public Page<Slip> cherche(String keyword, Pageable pageable){
        Page<Slip> Listofcherche;
        if(keyword!=null){
            Listofcherche =slipRepositorie.chercher(keyword,1,0,pageable);
        }
        else {
            Listofcherche=slipRepositorie.findAll(pageable);
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

          slipRepositorie.save(EditSlip);

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

            s.setNumerocheque(bankcheck.getCheckNumber());
        }


    }


}
