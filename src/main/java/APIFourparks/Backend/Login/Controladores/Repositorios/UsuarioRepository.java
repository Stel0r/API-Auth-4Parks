package APIFourparks.Backend.Login.Controladores.Repositorios;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import APIFourparks.Backend.Login.Logica.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,String>{

    @Query(value = "UPDATE USUARIO SET I_ESTADO = 'A',N_INTENTOS_FALLIDOS WHERE N_NOMBRE_USUARIO = ?",nativeQuery = true)
    public void desbloquear(String user);

}
