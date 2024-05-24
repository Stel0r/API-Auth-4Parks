package APIFourparks.Backend.Login.Controladores.Repositorios;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import APIFourparks.Backend.Login.Logica.Gerente;

@Repository
public interface GerenteRepository extends CrudRepository<Gerente,String>{
    @Query(value = "select u.*, a.K_COD_GERENTE from GERENTE a, USUARIO u where a.N_NOMBRE_USUARIO = u.N_NOMBRE_USUARIO and a.N_NOMBRE_USUARIO = ?",nativeQuery = true)
    public Map<String,Object> obtenerGerente(String user);
}
