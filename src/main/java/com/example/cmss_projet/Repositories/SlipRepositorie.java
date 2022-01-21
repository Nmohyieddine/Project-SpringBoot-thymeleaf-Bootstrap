package com.example.cmss_projet.Repositories;

import com.example.cmss_projet.Service.Services;
import com.example.cmss_projet.entities.Slip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public interface SlipRepositorie extends JpaRepository<Slip,Long> {



    Page<Slip> findAllByStatusAndStatusPaiement(int status,int statusPaiement, Pageable pageable);

    Page<Slip> findAllByStatus(int status, Pageable pageable);

    List<Slip> findAllByStatusPaiement(int status);

    @Query("select p from Slip p where p.status=?2 and p.statusPaiement=?3 and p.contractedCode like %?1%")
    Page<Slip> chercher(String keyword,int Status,int StatusPaiement,Pageable pageable);

    @Query("select p from Slip p where   p.contractedCode like %?1%")
    Page<Slip> chercherAll(String keyword,Pageable pageable);

    @Query("select p from Slip p where  p.status=?2 and p.contractedCode like %?1%")
    Page<Slip> chercherAllVentiler(String keyword,int status,Pageable pageable);


    List<Slip> findBySlipCodeIn(List<Long> listLong);

    Slip findBySlipCode(Long Slipcode);


    @Modifying
    @Query("update Slip p set p.contractedCode=?2 ,p.ReceptionDate=?3,p.MonthSlip=?4,p.YearSlip=?5,p.TotalAmount=?6,p.ChangeDate=?7,p.SendAssistantDate=?8,p.BackAssistantDate=?9 where p.slipCode=?1 " )
    void EditSlipByid( Long Slipcode,String contractedcode, LocalDate ReceptionDate,int MonthSlip,int YearSlip,float totalamount,LocalDate ChangeDate,LocalDate SendAssistantDate,LocalDate BackAssistantDate);

    List<Slip> findByStatusAndContractedCode(int status,String contractedcode);

    List<Slip> findByStatus(int status);

    List<Slip> findByStatusPaiementAndContractedCode(int StatusPaiement,String ContractedCode);

    @Query("select s.MonthSlip from Slip s where s.YearSlip=?1 ")   
    List<Integer> ListOfMonthByYear(Long Year);




    @Query("select S from Slip S where S.contractedCode=?2 and S.statusPaiement=?1 and S.ReceptionDate between ?3 and ?4")
     List<Slip> SlipPaiementwithDate(int statusPaiement,String contracted,LocalDate dateBefore,LocalDate dateAfter);

    @Query("select S from Slip S where S.contractedCode=?2 and S.status=?1 and S.ReceptionDate between ?3 and ?4")
    List<Slip> SlipVentilationWithDate(int status, String contracted, LocalDate dateBefore, LocalDate dateAfter);


    @Query("select S from Slip S where  S.statusPaiement=?1 and S.ReceptionDate between ?2 and ?3")
    List<Slip> SlipPaiementwithDateAll(int statusPaiement,LocalDate dateBefore,LocalDate dateAfter);

    @Query("select S from Slip S where S.status=?1 and S.ReceptionDate between ?2 and ?3")
    List<Slip> SlipVentilationWithDateAll(int status, LocalDate dateBefore, LocalDate dateAfter);


}
