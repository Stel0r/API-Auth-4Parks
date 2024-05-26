package APIFourparks.Backend.Login.Controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.Repositorios.AdminRespository;

@RestController
@CrossOrigin
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    public AdminRespository adminRespository;
    
    @GetMapping("/obtener/{user}")
    public Map<String,Object> obtenerAdmin(@PathVariable(value = "user") String user){
        return adminRespository.obtenerAdmin(user);
    }

    @GetMapping("/obtenerUsuarios")
    public Map<String,Object> obtenerUsuarios(){
        return adminRespository.obtenerUsuariosEstado();
    }


}
