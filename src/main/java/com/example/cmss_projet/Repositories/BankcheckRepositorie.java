package com.example.cmss_projet.Repositories;

import com.example.cmss_projet.entities.Bankcheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankcheckRepositorie extends JpaRepository<Bankcheck,Long> {



       @Query("select b from Bankcheck b where b.CheckNumber=?1 ")
       Bankcheck findCheckNumber(int number);


       Page<Bankcheck> findByCheckNumber(int numero, Pageable pageable);

       Page<Bankcheck> findAll(Pageable pageable);


}
