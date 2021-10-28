package com.example.cmss_projet.Controler;

import com.example.cmss_projet.ExelExport.InvoiceExport;
import com.example.cmss_projet.ExelExport.SlipExport;
import com.example.cmss_projet.Repositories.ContractedRepositorie;
import com.example.cmss_projet.Repositories.InvoiceRepositorie;
import com.example.cmss_projet.Repositories.SlipRepositorie;
import com.example.cmss_projet.entities.Invoice;
import com.example.cmss_projet.entities.Slip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ExportControler {

    @Autowired
    private SlipRepositorie slipRepositorie;
    @Autowired
    private ContractedRepositorie contractedRepositorie;
    @Autowired
    private InvoiceRepositorie invoiceRepositorie;


    @PostMapping("/exportSlipVentiler")
    public void exportToExcelVentiler(HttpServletResponse response, @RequestParam(value = "StatusPaiement") int StatusVentilation,@RequestParam(value = "Conventionne")String conventionne) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        if(!conventionne.equals("true")){
            if(StatusVentilation==1){
                String headerValue = "attachment; filename=BordereauxVentiler" +currentDateTime+".xlsx";
                response.setHeader(headerKey, headerValue);

            }else {
                String headerValue = "attachment; filename=BordereauxNonVentiler" +currentDateTime+".xlsx";
                response.setHeader(headerKey, headerValue);

            }

            List<Slip> listSlip= slipRepositorie.findByStatus(StatusVentilation);
            SlipExport excelExporter = new SlipExport(listSlip);
            excelExporter.export(response);



        }else {

            String denomination=contractedRepositorie.finddenomination(conventionne);

            if(StatusVentilation==1){
                String headerValue = "attachment; filename=Bordereau_"+denomination+"_Ventiler" +currentDateTime+".xlsx";
                response.setHeader(headerKey, headerValue);

            }else {
                String headerValue = "attachment; filename=Bordereaux_"+denomination+"_NonVentiler" +currentDateTime+".xlsx";
                response.setHeader(headerKey, headerValue);

            }

            List<Slip> listSlip= slipRepositorie.findByStatusAndContractedCode(StatusVentilation,conventionne);
            SlipExport excelExporter = new SlipExport(listSlip);
            excelExporter.export(response);



        }


    }

    @PostMapping("/exportSlipPaiement")
    public void exportToExcelPaiement(HttpServletResponse response, @RequestParam(value = "StatusPaiement") int StatusPaiement,@RequestParam(value = "Conventionne")String conventionne) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerValue = "attachment; filename=BordereauxPayer" +currentDateTime+".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Slip> listSlipPaiement= slipRepositorie.findByStatusPaiementAndContractedCode(StatusPaiement,conventionne);
        SlipExport excelExporter = new SlipExport(listSlipPaiement);
        excelExporter.export(response);




    }
    @PostMapping("/exportInvoicePaiement")
    public void exportToExcelInvoice(HttpServletResponse response, @RequestParam(value = "slipcode") Long slipcode) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerValue = "attachment; filename=BordereauxPayer" +currentDateTime+".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Invoice> listInvoice= invoiceRepositorie.findBySlipCode(slipcode);

        InvoiceExport excelExporter = new InvoiceExport(listInvoice);

        excelExporter.export(response);




    }

}
