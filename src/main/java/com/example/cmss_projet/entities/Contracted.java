package com.example.cmss_projet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Contracted{
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     //private String contractedCode;
     private String denomination;
     private String Speciality;
     private String City;
     private String Mail;
     private String PhoneNumber;
     private int DurationToPay;

     @OneToMany(mappedBy = "contracted")
     private List<Slip> Slip;

}
