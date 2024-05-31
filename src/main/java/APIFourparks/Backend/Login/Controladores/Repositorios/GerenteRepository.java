package APIFourparks.Backend.Login.Controladores.Repositorios;

import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import APIFourparks.Backend.Login.Logica.Gerente;
import jakarta.transaction.Transactional;

@Repository
public interface GerenteRepository extends CrudRepository<Gerente,String>{

    @Query(value = "select u.N_NOMBRE_USUARIO, u.O_EMAIL, u.I_ROL, u.I_ESTADO, u.N_PRIMER_NOMBRE, u.N_SEGUNDO_NOMBRE, u.N_PRIMER_APELLIDO, u.N_SEGUNDO_APELLIDO, a.K_COD_GERENTE from GERENTE a, USUARIO u where a.N_NOMBRE_USUARIO = u.N_NOMBRE_USUARIO and a.N_NOMBRE_USUARIO = ?",nativeQuery = true)
    public Map<String,Object> obtenerGerente(String user);

    

    @Query(value = "insert into LOGIN_GERENTE values(?)",nativeQuery = true)
    public void insertarLoginGerente(String user);
}
