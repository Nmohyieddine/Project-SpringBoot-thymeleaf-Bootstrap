package com.example.cmss_projet.ExelExport;

import com.example.cmss_projet.entities.Invoice;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class InvoiceExport {


    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Invoice> listInvoice;


    public InvoiceExport(List<Invoice> listInvoice) {
        this.listInvoice = listInvoice;
        workbook = new XSSFWorkbook();
        sheet=workbook.createSheet("Invoices");

    }


    private void writeHeaderRow() {

        Row row = sheet.createRow(0);
        Cell cell=row.createCell(0);
        cell.setCellValue("Invoice Code");

        cell=row.createCell(1);
        cell.setCellValue("Slip Code");

        cell=row.createCell(2);
        cell.setCellValue("Régie");

        cell=row.createCell(3);
        cell.setCellValue("Montant");

        cell=row.createCell(4);
        cell.setCellValue("Agent");

        cell=row.createCell(5);
        cell.setCellValue("Ascendant");



    }


    private void writeDataRows() {
        int rowCounter=1;



        for(Invoice invoice:listInvoice){
            Row row=sheet.createRow(rowCounter++);

            Cell cell=row.createCell(0);
            cell.setCellValue(invoice.invoiceCode);

            cell=row.createCell(1);
            cell.setCellValue(invoice.slipCode);

            cell=row.createCell(2);
            cell.setCellValue(invoice.Regie);

            cell=row.createCell(3);
            cell.setCellValue(invoice.amount);

            cell=row.createCell(4);
            cell.setCellValue(invoice.Agent);

            cell=row.createCell(5);
            cell.setCellValue(invoice.Ascendant);





        }



    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

}
