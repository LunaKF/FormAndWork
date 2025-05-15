package com.proyecto.FormAndWork.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.FormAndWork.bean.LogindataBean;
import com.proyecto.FormAndWork.entity.AlumnoEntity;
import com.proyecto.FormAndWork.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    AuthService oAuthService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogindataBean oLogindataBean) {
        if (oAuthService.checkLogin(oLogindataBean)) {
            return ResponseEntity.ok("\"" + oAuthService.getToken(oLogindataBean.getEmail()) + "\"");
        } else {
            return ResponseEntity.status(401).body("\"" + "Error de autenticación" + "\"");
        }
    }

         @GetMapping("/session/active")
    public ResponseEntity<Boolean> isSessionActive() {
        return ResponseEntity.ok(oAuthService.isSessionActive());
    }

    @GetMapping("/session/email")
    public ResponseEntity<String> getEmailFromSession() {
        try {
            return ResponseEntity.ok(oAuthService.getEmailSession());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("No hay sesión activa");
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<AlumnoEntity> getUsuario() {
        try {
            return ResponseEntity.ok(oAuthService.getUsuarioFromToken());
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/alumno")
    public ResponseEntity<Boolean> isAlumno() {
        return ResponseEntity.ok(oAuthService.isAlumno());
    }

    @GetMapping("/empresa")
    public ResponseEntity<Boolean> isEmpresa() {
        return ResponseEntity.ok(oAuthService.isEmpresa());
    }

    @GetMapping("/admin")
    public ResponseEntity<Boolean> isAdmin() {
        return ResponseEntity.ok(oAuthService.isAdmin());
    }
}
