package APIFourparks.Backend.Login.Controladores;

import java.math.BigInteger;
import java.sql.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import APIFourparks.Backend.Login.Controladores.Repositorios.ClienteRepository;
import jakarta.websocket.server.PathParam;


class InfoCardSend {
    public String idCard;
    public String namePro;
    public String numberCard;
    public int csv;
    public String dateExp;
    public long numDoc;
    public String typeDoc;
}

class InfoDeleteCard {
    public String idCard;
}

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ClientController {

    @Autowired
    public ClienteRepository clienteRepository;


    @GetMapping("/misdatos/{usuario}")
    public Map<String,Object> misDatosUsuario(@PathVariable(value = "usuario") String usuario){
        return clienteRepository.obtenerMisDatos(usuario);
    }
    
}
