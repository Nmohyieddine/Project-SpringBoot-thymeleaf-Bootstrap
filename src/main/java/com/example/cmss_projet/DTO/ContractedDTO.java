package com.example.cmss_projet.DTO;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractedDTO {

    public String ContractedCode;
    public boolean Ventiler=false;
    public boolean NonVentiler=false;
    public boolean Payer=false;
    public boolean NonPayer=false;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate DateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate DateFin;
}
