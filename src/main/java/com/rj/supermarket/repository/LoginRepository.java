package com.rj.supermarket.repository;

import com.rj.supermarket.domain.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("loginRepository")
public interface LoginRepository extends JpaRepository<Login, UUID> {

    Login findByEmailAndSenha(String email, String senha);

    Login getByEmail(String email);
}
