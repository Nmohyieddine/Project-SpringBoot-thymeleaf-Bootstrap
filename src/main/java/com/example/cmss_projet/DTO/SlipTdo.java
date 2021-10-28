package com.example.cmss_projet.DTO;

import com.example.cmss_projet.entities.Slip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SlipTdo {

    public  List<Slip> slips;

    public void addSlip(Slip slip){

        this.slips.add(slip);

    }

}
