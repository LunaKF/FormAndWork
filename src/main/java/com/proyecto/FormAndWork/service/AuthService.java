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

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Autowired
    JWTService JWTHelper;

    @Autowired
    AlumnoRepository oAlumnoRepository;

    @Autowired
    EmpresaRepository oEmpresaRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    /*   public boolean checkLogin(LogindataBean oLogindataBean) {

        if (oLogindataBean.getEmail() == null || oLogindataBean.getPassword() == null) {
            return false;
        }

        if (oLogindataBean.getEmail().isEmpty() || oLogindataBean.getPassword().isEmpty()) {
            return false;
        }

        if (oLogindataBean.getEmail().equalsIgnoreCase("admin@ausias.es") && oLogindataBean.getPassword().equalsIgnoreCase("admin1234")) { // cambiar por hash 
            return true;
        }

        //cualquier persona que escriba el email de un alumno o empresa puede iniciar sesión, aunque escriba cualquier contraseña
        
        if (oAlumnoRepository.findByEmail(oLogindataBean.getEmail()).isEmpty()) {
            if (oEmpresaRepository.findByEmail(oLogindataBean.getEmail()).isEmpty()) {
                return false;
            } else{
                return true;
            }
        } else {
            return true;
        }

    }
     */
    public boolean checkLogin(LogindataBean oLogindataBean) {
        if (oLogindataBean.getEmail() == null || oLogindataBean.getPassword() == null) {
            return false;
        }
        if (oLogindataBean.getEmail().isEmpty() || oLogindataBean.getPassword().isEmpty()) {
            return false;
        }

        // Login admin
        if (oLogindataBean.getEmail().equalsIgnoreCase("admin@ausias.es")
                && oLogindataBean.getPassword().equals("admin1234")) {
            return true;
        }

        // Buscar alumno
        var alumnoOpt = oAlumnoRepository.findByEmail(oLogindataBean.getEmail());
        if (alumnoOpt.isPresent()) {
            return alumnoOpt.get().getPassword().equals(oLogindataBean.getPassword());
        }

        // Buscar empresa
        var empresaOpt = oEmpresaRepository.findByEmail(oLogindataBean.getEmail());
        if (empresaOpt.isPresent()) {
            return empresaOpt.get().getPassword().equals(oLogindataBean.getPassword());
        }

        return false;
    }
/* 
    private Map<String, String> getClaims(String email) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", email);
        return claims;
    }

*/   
 private Map<String, String> getClaims(String email) {
    Map<String, String> claims = new HashMap<>();
    claims.put("email", email);

    if (email.equalsIgnoreCase("admin@ausias.es")) {
        claims.put("tipoUsuario", "admin");
    } else if (oEmpresaRepository.findByEmail(email).isPresent()) {
        claims.put("tipoUsuario", "empresa");
    } else if (oAlumnoRepository.findByEmail(email).isPresent()) {
        claims.put("tipoUsuario", "alumno");
    } else {
        claims.put("tipoUsuario", "desconocido");
    }

    return claims;
}


    public String getToken(String email) {
        return JWTHelper.generateToken(getClaims(email));
    }

    public EmpresaEntity getEmpresaFromToken() {
        if (oHttpServletRequest.getAttribute("email") == null) {
            throw new UnauthorizedAccessException("No hay usuario en la sesión");
        } else {
            String email = oHttpServletRequest.getAttribute("email").toString();
            return oEmpresaRepository.findByEmail(email).get();
        }
    }

    public AlumnoEntity getAlumnoFromToken() {
        if (oHttpServletRequest.getAttribute("email") == null) {
            throw new UnauthorizedAccessException("No hay usuario en la sesión");
        } else {
            String email = oHttpServletRequest.getAttribute("email").toString();
            return oAlumnoRepository.findByEmail(email).get();
        }
    }

    public boolean isSessionActive() {
        return oHttpServletRequest.getAttribute("email") != null;
    }

    public String getEmailSession() {
    Object emailAttr = oHttpServletRequest.getAttribute("email");
    if (emailAttr == null) {
        throw new UnauthorizedAccessException("No hay usuario en la sesión");
    }
    return emailAttr.toString();
}


    public Boolean isAlumno() {
        String emailSession = oHttpServletRequest.getAttribute("email").toString();
        if (oAlumnoRepository.findByEmail(emailSession).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isEmpresa() {
        String emailSession = oHttpServletRequest.getAttribute("email").toString();
        if (oEmpresaRepository.findByEmail(emailSession).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isAdmin() {
        String emailSession = oHttpServletRequest.getAttribute("email").toString();
        if (emailSession.equals("admin@ausias.es")) {
            return true;
        } else {
            return false;
        }
    }

    // Metodos para comprobar si el usuario autenticado es el dueño de los datos 
    public Boolean isAlumnoWithItsOwnData(Long id) {
        String emailSession = oHttpServletRequest.getAttribute("email").toString();

        Optional<AlumnoEntity> alumnoOpt = oAlumnoRepository.findByEmail(emailSession);

        if (alumnoOpt.isPresent()) {
            AlumnoEntity alumno = alumnoOpt.get();
            return alumno.getId().equals(id);
        } else {
            return false;
        }
    }

    public Boolean isEmpresaWithItsOwnData(Long id) {
        String emailSession = oHttpServletRequest.getAttribute("email").toString();

        Optional<EmpresaEntity> empresaOpt = oEmpresaRepository.findByEmail(emailSession);

        if (empresaOpt.isPresent()) {
            EmpresaEntity empresa = empresaOpt.get();
            return empresa.getId().equals(id);
        } else {
            return false;
        }
    }

}
