package com.example.cmss_projet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long invoiceCode;

    public Long slipCode;
    public String Regie;
    public float amount;
    public float amountNet;
    public float Agent;
    public float Ascendant;
    public float precompte;
    @Column(columnDefinition = "integer default 0")
    public int status;
    public String ContractedName;

}
