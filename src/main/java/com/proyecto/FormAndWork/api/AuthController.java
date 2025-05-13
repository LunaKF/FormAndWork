package com.proyecto.FormAndWork.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.FormAndWork.bean.LogindataBean;
import com.proyecto.FormAndWork.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogindataBean loginData) {
        String token = authService.login(loginData.getEmail(), loginData.getPassword());
        if (token != null) {
            return ResponseEntity.ok("\"" + token + "\"");
        } else {
            return ResponseEntity.status(401).body("\"Credenciales incorrectas\"");
        }
    }
}
