package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
}
