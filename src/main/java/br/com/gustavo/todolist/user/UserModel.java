package br.com.gustavo.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;

    @CreationTimestamp
    private LocalDateTime createdAT;

    /*MÉTODOS
     * getters utilizado para obter (acessar) o valor de uma propriedade
     * setters utilizado para alterar o valor de uma propriedade
     */


}