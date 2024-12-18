package br.com.gustavo.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.gustavo.todolist.user.InterUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTeskAuth extends OncePerRequestFilter {

    @Autowired
    private InterUserRepository interUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/tasks/"))
        {

            // pegar a autenticação (usuario e senha)
            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);

            String credentials[] = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // valida o usuario
            var user = this.interUserRepository.findByUsername(username);
            if (user == null) 
            {
                response.sendError(401, "Usuário inválido");
            } else 
            {
                // valida a senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) 
                {
                // segue o fluxo
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else 
                {
                    response.sendError(401, "Senha inválida");
                }
            }
        } else
        {
            filterChain.doFilter(request, response);
        }
    }

}
