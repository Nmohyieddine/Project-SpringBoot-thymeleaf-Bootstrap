package com.example.cmss_projet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Bankcheck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Checkid;
    public int CheckNumber;
    private String Bank;
    private float amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate CheckDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate CheckDateRetirer;




}
