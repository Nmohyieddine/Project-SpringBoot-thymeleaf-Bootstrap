package com.example.cmss_projet.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data @AllArgsConstructor @NoArgsConstructor
public class BankchekDTO {

    public Long checkid;
    public int CheckNumber;
    public String Bank;
    public Float Amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate CheckDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate DateRetirerCheque;
}
