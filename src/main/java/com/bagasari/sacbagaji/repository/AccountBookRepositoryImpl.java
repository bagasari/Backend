package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.AccountBook;
import com.bagasari.sacbagaji.model.entity.QAccountBook;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountBookRepositoryImpl implements AccountBookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public AccountBook findFirstByOrderByUpdateTimeDesc(String email) {

        QAccountBook qa = QAccountBook.accountBook;

        return queryFactory
                .select(qa)
                .from(qa)
                .where(qa.user.email.eq(email))
                .orderBy(qa.updateTime.desc())
                .fetchFirst();
    }
}
