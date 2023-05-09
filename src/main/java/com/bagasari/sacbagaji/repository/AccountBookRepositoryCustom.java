package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.AccountBook;

public interface AccountBookRepositoryCustom {

    AccountBook findFirstByOrderByUpdateTimeDesc(String email);

}
