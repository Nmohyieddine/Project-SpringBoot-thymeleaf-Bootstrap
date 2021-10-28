package com.example.cmss_projet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Regie {

    @Id
    public Long idRegie;
    public  String RegieName;
}
