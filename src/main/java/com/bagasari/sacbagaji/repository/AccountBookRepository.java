package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findAllByUserId(Long id);
}
