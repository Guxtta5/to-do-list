package br.com.gustavo.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InterUserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
}
