package com.example.cmss_projet.Repositories;

import com.example.cmss_projet.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepositorie extends JpaRepository<Invoice,Long> {

     List<Invoice> findInvoiceBySlipCode(Long slipcode);

    @Query("select SUM(p.amount) from Invoice p where ?1=p.slipCode")
     Float sumamount(Long id);

    Page<Invoice> findInvoiceByStatus(int status, Pageable pageable);

    List<Invoice> findBySlipCode(Long slipcode);




}
