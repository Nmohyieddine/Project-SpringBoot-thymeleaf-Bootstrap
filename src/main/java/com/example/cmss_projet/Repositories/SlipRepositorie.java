package com.example.cmss_projet.Repositories;

import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Slip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.swing.*;
import java.time.LocalDate;
import java.util.List;



public interface SlipRepositorie extends JpaRepository<Slip,Long> {



    Page<Slip> findAllByStatusAndStatusPaiement(int status,int statusPaiement, Pageable pageable);

    Page<Slip> findAllByStatus(int status, Pageable pageable);

    List<Slip> findAllByStatusPaiement(int status);

    @Query("select p from Slip p where p.status=?2 and p.statusPaiement=?3 and p.contractedCode like %?1%")
    Page<Slip> chercher(String keyword,int Status,int StatusPaiement,Pageable pageable);


    List<Slip> findBySlipCodeIn(List<Long> listLong);

    Slip findBySlipCode(Long Slipcode);


    @Modifying
    @Query("update Slip p set p.contractedCode=?2 ,p.ReceptionDate=?3,p.MonthSlip=?4,p.YearSlip=?5,p.TotalAmount=?6,p.ChangeDate=?7,p.SendAssistantDate=?8,p.BackAssistantDate=?9 where p.slipCode=?1 " )
    void EditSlipByid( Long Slipcode,String contractedcode, LocalDate ReceptionDate,int MonthSlip,int YearSlip,float totalamount,LocalDate ChangeDate,LocalDate SendAssistantDate,LocalDate BackAssistantDate);

    List<Slip> findByStatusAndContractedCode(int status,String contractedcode);

    List<Slip> findByStatus(int status);

    List<Slip> findByStatusPaiementAndContractedCode(int StatusPaiement,String ContractedCode);






}
