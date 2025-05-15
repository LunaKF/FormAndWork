package com.proyecto.FormAndWork.service;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.FormAndWork.bean.LogindataBean;
import com.proyecto.FormAndWork.entity.AlumnoEntity;
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

    public boolean checkLogin(LogindataBean oLogindataBean) {

        if (oLogindataBean.getEmail() == null || oLogindataBean.getPassword() == null) {
            return false;
        }

        if (oLogindataBean.getEmail().isEmpty() || oLogindataBean.getPassword().isEmpty()) {
            return false;
        }

        if (oLogindataBean.getEmail().equalsIgnoreCase("admin@ausias.es") || oLogindataBean.getPassword().equalsIgnoreCase("admin1234")) { // cambiar por hash 
            return true;
        }

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

    private Map<String, String> getClaims(String email) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", email);
        return claims;
    };

    public String getToken(String email) {
        return JWTHelper.generateToken(getClaims(email));
    }

    public AlumnoEntity getUsuarioFromToken() {
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

    public String getEmailSession(){
        return oHttpServletRequest.getAttribute("email").toString();
    }

    public Boolean isAlumno(){
        String emailSession=oHttpServletRequest.getAttribute("email").toString();
        if (oAlumnoRepository.findByEmail(emailSession).isPresent()){
            return true;
        }else{
            return false;
        }        
    }
   
    public Boolean isEmpresa(){
        String emailSession=oHttpServletRequest.getAttribute("email").toString();
        if (oEmpresaRepository.findByEmail(emailSession).isPresent()){
            return true;
        }else{
            return false;
        }        
    }
   
    public Boolean isAdmin(){
        String emailSession=oHttpServletRequest.getAttribute("email").toString();
        if (emailSession.equals("admin@ausias.es")){
            return true;
        }else{
            return false;
        }        
    }
   


}