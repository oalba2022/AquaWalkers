package com.example.aquawalkers.service;

import com.example.aquawalkers.models.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UserService {
    public class RegistroUsuarios {
        private Map<Long, User> usuariosRegistrados;

        public RegistroUsuarios() {
            this.usuariosRegistrados = new HashMap<>();
        }


        public void registrarUsuario(Long id, String nombre, String correo, String contrasena) {
            User nuevoUsuario = new User(id, nombre, correo, contrasena);
            usuariosRegistrados.put(id, nuevoUsuario);
            System.out.println("Registro exitoso. ¡Bienvenido, " + nombre + "!");
        }

        public User obtenerUsuarioPorID(Long id) {
            return usuariosRegistrados.get(id);
        }


    }


}