package com.example.cmss_projet.ExelExport;

import com.example.cmss_projet.entities.Slip;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class SlipExport {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Slip> listSlip;


    public SlipExport(List<Slip> listSlip) {
        this.listSlip = listSlip;
        workbook = new XSSFWorkbook();
        sheet=workbook.createSheet("Slips");

    }


    private void writeHeaderRow() {

        Row row = sheet.createRow(0);
        Cell cell=row.createCell(0);
        cell.setCellValue("Code Borderau");

        cell=row.createCell(1);
        cell.setCellValue("Code conventionne");

        cell=row.createCell(2);
        cell.setCellValue("Date de reception");

        cell=row.createCell(3);
        cell.setCellValue("Mois Borderau");

        cell=row.createCell(4);
        cell.setCellValue("Année Bordereau");

        cell=row.createCell(5);
        cell.setCellValue("Montant Total");

        cell=row.createCell(6);
        cell.setCellValue("Date Rectif");

        cell=row.createCell(7);
        cell.setCellValue("Date envoi contabilité");

        cell=row.createCell(8);
        cell.setCellValue("Date envoi assistance");

        cell=row.createCell(9);
        cell.setCellValue("Date reception assistance");






    }


    private void writeDataRows() {
        int rowCounter=1;



        for(Slip slip:listSlip){
            Row row=sheet.createRow(rowCounter++);

            Cell cell=row.createCell(0);
            cell.setCellValue(slip.slipCode);

            cell=row.createCell(1);
            cell.setCellValue(slip.contractedCode);

            cell=row.createCell(2);
            cell.setCellValue((LocalDate) slip.ReceptionDate);


            cell=row.createCell(3);
            cell.setCellValue(slip.MonthSlip);

            cell=row.createCell(4);
            cell.setCellValue(slip.YearSlip);

            cell=row.createCell(5);
            cell.setCellValue(slip.TotalAmount);

            cell=row.createCell(6);
            cell.setCellValue(slip.ChangeDate);


            cell=row.createCell(7);
            cell.setCellValue(slip.SendContability);


            cell=row.createCell(8);
            cell.setCellValue(slip.SendAssistantDate);

            cell=row.createCell(9);
            cell.setCellValue(slip.BackAssistantDate);





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
