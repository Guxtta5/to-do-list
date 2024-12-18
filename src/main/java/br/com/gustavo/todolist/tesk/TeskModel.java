package br.com.gustavo.todolist.tesk;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tesk")
public class TeskModel {
    /**
     * ID
     * Usuário (ID_usuário)
     * Descrição
     * Título
     * Data de Início
     * Data de Término
     * Prioridade
     */

     @Id
     @GeneratedValue(generator = "UUID")
     private UUID id;
     private String description;

     @Column(length = 50)
     private String title;
     private LocalDateTime startAt;
     private LocalDateTime endAt;
     private String priority;

     private UUID idUser;
     
     @CreationTimestamp
     private LocalDateTime createdAT;

     public void setTitle(String title) throws Exception{
        if (title.length() > 50) {
            throw new Exception("O título deve ter no máximo 50 caracteres.");
            
        }
         this.title = title;
     }
}
