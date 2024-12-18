package br.com.gustavo.todolist.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificadfor
 * public (qualquer um pode acessar)
 * private (somente o controller pode acessar)
 * protected (somente o controller ou subclasses)
 * default (somente o controller pode acessar)
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private InterUserRepository interUserRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.interUserRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado");
        } 

        var passwordHashred = BCrypt.withDefaults()
        .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCrated = this.interUserRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCrated);
    }

}
