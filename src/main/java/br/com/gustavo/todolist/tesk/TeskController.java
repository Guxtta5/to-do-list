package br.com.gustavo.todolist.tesk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavo.todolist.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.var;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/tasks")
public class TeskController {

    @Autowired
    private TeskRepository teskRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody TeskModel teskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        teskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(teskModel.getStartAt()) || currentDate.isAfter(teskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início / data de término deve ser maior que a data atual");
        }

        if (teskModel.getStartAt().isAfter(teskModel.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser menor que a data de término");
        }

        var task = this.teskRepository.save(teskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TeskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.teskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TeskModel teskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.teskRepository.findById(id).orElse(null);

        //verifica se a tarefa existe
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Tarefa nao encontrada");
        }

        //verifica se o usuario tem permissão para alterar a tarefa
        var idUser = request.getAttribute("idUser");
        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("O usuario não tem permição para editar essa tarefa");
        }
        
        Util.copyNonNullProperties(teskModel, task);
        var taskUpdated = this.teskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }

}
