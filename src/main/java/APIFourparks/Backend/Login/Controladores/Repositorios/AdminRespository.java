package APIFourparks.Backend.Login.Controladores.Repositorios;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import APIFourparks.Backend.Login.Logica.Administrador;

@Repository
public interface AdminRespository extends CrudRepository<Administrador,String>{
    
    @Query(value = "select u.N_NOMBRE_USUARIO, u.O_EMAIL, u.I_ROL, u.I_ESTADO, u.N_PRIMER_NOMBRE, u.N_SEGUNDO_NOMBRE, u.N_PRIMER_APELLIDO, u.N_SEGUNDO_APELLIDO, a.K_COD_ADMINISTRADOR from ADMINISTRADOR a, USUARIO u where a.N_NOMBRE_USUARIO = u.N_NOMBRE_USUARIO and a.N_NOMBRE_USUARIO = ?",nativeQuery = true)
    public Map<String,Object> obtenerAdmin(String user);

    @Query(value = "select select c.I_TIPO_DOC tipoDoc, c.K_NUM_DOCUMENTO numDocumento, CONCAT(u.N_PRIMER_NOMBRE, ' ' ,u.N_PRIMER_APELLIDO) as nombre,u.O_EMAIL as email, u.I_ESTADO as estado from CLIENTE c,USUARIO u where c.N_NOMBRE_USUARIO = u.N_NOMBRE_USUARIO;",nativeQuery = true)
    public Map<String,Object> obtenerUsuariosEstado();
}
