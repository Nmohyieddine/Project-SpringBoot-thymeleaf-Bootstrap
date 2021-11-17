package com.example.cmss_projet.Repositories;

import com.example.cmss_projet.entities.Contracted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractedRepositorie extends JpaRepository<Contracted,Long> {

    @Query("select c.denomination from Contracted c where c.contractedCode=?1")
    String finddenomination(String CodeConventionne);

    Contracted findByContractedCode(String contractedcode);

}
