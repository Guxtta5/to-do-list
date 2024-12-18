package br.com.gustavo.todolist.tesk;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TeskRepository extends JpaRepository<TeskModel, UUID> {
    List<TeskModel> findByIdUser(UUID idUser);
    TeskModel findByIdAndIdUser(UUID id, UUID idUser);

}
