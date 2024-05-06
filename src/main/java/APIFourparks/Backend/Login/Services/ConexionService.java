package APIFourparks.Backend.Login.Services;

import java.rmi.server.ExportException;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import APIFourparks.Backend.Login.Controladores.RequestBody.RegistroClienteBody;
import APIFourparks.Backend.Login.Logica.Conexion;
import jakarta.validation.OverridesAttribute.List;



public class ConexionService {
    private static ConexionService servicio;
    private Conexion conexion;

    private ConexionService() {
        conexion = Conexion.obtenerConexion();
    }

    public static ConexionService obtenerServicio() {
        if (servicio == null) {
            servicio = new ConexionService();
        }
        return servicio;
    }

    public String logearUsuario(String user, String pass) throws Exception{
        java.util.List<Map<String, Object>> resultado = conexion.SelectQuery("select * from USUARIO where N_NOMBRE_USUARIO='"+user+"' and O_CONTRASEÑA='"+pass+"'");
        if(resultado.size() == 0){
            resultado = conexion.SelectQuery("select * from USUARIO where N_NOMBRE_USUARIO='"+user+"' ");
            if(resultado.size()!= 0){
                conexion.EjecutarQuery("UPDATE USUARIO SET N_INTENTOS_FALLIDOS = N_INTENTOS_FALLIDOS+1 WHERE N_NOMBRE_USUARIO ='"+user+"'");
                if (((java.math.BigDecimal)resultado.get(0).get("N_INTENTOS_FALLIDOS")).intValue() >= 2){
                    throw new Exception("Su cuenta ha sido bloqueada, por favor comuniquese con un Administrador");
                }
            }
            throw new Exception("Usuario o contraseña incorrecta");
        }else if(((java.math.BigDecimal)resultado.get(0).get("N_INTENTOS_FALLIDOS")).intValue() >= 3){
            throw new Exception("Su cuenta ha sido bloqueada, por favor comuniquese con un Administrador");
        }
        return "conectado !";
    }

    public String logearUsuario(RegistroClienteBody body) throws Exception{
        try {
            conexion.EjecutarQuery("insert into USUARIO values('"+body.userName+"','"+body.password+"','"+body.email+"','C','A','"+body.firstName+"','"+body.secondName+"','"+body.firstLastName+"','"+body.secondLastName+"',0)");
            conexion.EjecutarQuery("insert into CLIENTE values('"+body.typeID+"',"+body.NID+","+body.numberCell+",'"+body.userName+"',0)");
            return "Registro Exitoso!";
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate entry")){
                if(e.getMessage().contains("USUARIO")){
                    throw new Exception("El usuario "+body.userName+ " ya esta registrado"); 
                }else if(e.getMessage().contains("CLIENTE")){
                    conexion.EjecutarQuery("delete from USUARIO where N_NOMBRE_USUARIO='"+body.userName+"'");
                    throw new Exception("La persona con documento "+body.typeID+body.NID+ " ya esta registrada");
                }
            }
            throw new Exception("Oops, Algo salio mal, nuestros Monos Ingenieros estan trabajando en arreglarlo!");
        }
        
    }

}
