package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.entity.Authority;
import com.bagasari.sacbagaji.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
