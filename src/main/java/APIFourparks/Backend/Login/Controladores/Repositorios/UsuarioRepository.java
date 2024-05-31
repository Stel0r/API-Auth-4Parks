package APIFourparks.Backend.Login.Controladores.Repositorios;

import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import APIFourparks.Backend.Login.Logica.Usuario;
import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,String>{

    @Modifying
    @Transactional
    @Query(value = "UPDATE USUARIO SET I_ESTADO = 'A',N_INTENTOS_FALLIDOS = 0 WHERE N_NOMBRE_USUARIO = ?",nativeQuery = true)
    public void desbloquear(String user);

}
