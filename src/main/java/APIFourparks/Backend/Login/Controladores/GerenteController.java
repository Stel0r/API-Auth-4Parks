package APIFourparks.Backend.Login.Controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.Repositorios.GerenteRepository;

@RestController
@RequestMapping("/gerente")
public class GerenteController {

    @Autowired
    public GerenteRepository gerenteRepository;
    
    @GetMapping("/obtener/{user}")
    public Map<String,Object> obtenerGerente(@PathVariable(value = "user") String user){
        return gerenteRepository.obtenerGerente(user);
    }

}
