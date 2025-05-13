package com.proyecto.FormAndWork.service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.bean.LogindataBean;
import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.entity.EmpresaEntity;
import com.proyecto.FormAndWork.exception.UnauthorizedAccessException;
import com.proyecto.FormAndWork.repository.AlumnoRepository;
import com.proyecto.FormAndWork.repository.EmpresaRepository;
import com.proyecto.FormAndWork.bean.LogindataBean;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AlumnoRepository oAlumnoRepository;

    @Autowired
    private EmpresaRepository oEmpresaRepository;


 public String login(String email, String password) {
        // Admin sin entidad
        if (email.equals("lunakhanjifustek@gmail.com") && password.equals("admin")) {
            return jwtService.generateToken(getClaims(email, "admin"));
        }

        if (oAlumnoRepository.findByEmailAndPassword(email, password).isPresent()) {
            return jwtService.generateToken(getClaims(email, "alumno"));
        }

        if (oEmpresaRepository.findByEmailAndPassword(email, password).isPresent()) {
            return jwtService.generateToken(getClaims(email, "empresa"));
        }

        return null;
    }

    private Map<String, String> getClaims(String email, String tipoUsuario) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("tipoUsuario", tipoUsuario);
        return claims;
    }

    public boolean isSessionActive() {
        return request.getAttribute("email") != null;
    }

    public boolean isAdmin() {
        return "admin".equals(getTipoUsuarioFromRequest());
    }

    public boolean isEmpresa() {
        return "empresa".equals(getTipoUsuarioFromRequest());
    }

    public boolean isAlumno() {
        return "alumno".equals(getTipoUsuarioFromRequest());
    }

    public String getTipoUsuarioFromToken() {
        return (String) request.getAttribute("tipoUsuario");
    }

    public String getEmailFromToken() {
        return (String) request.getAttribute("email");
    } 
    
    public String getTipoUsuarioFromRequest() {
        Object tipoUsuario = request.getAttribute("tipoUsuario");
        if (tipoUsuario == null) {
            throw new UnauthorizedAccessException("No hay tipo de usuario en la sesión");
        }
        return tipoUsuario.toString();
    }

    public String getEmailFromRequest() {
        Object email = request.getAttribute("email");
        if (email == null) {
            throw new UnauthorizedAccessException("No hay email en la sesión");
        }
        return email.toString();
    }
}
