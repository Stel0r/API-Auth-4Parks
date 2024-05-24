package APIFourparks.Backend.Login.Controladores.Repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import APIFourparks.Backend.Login.Logica.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,String>{
}
