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
            throw new Exception("Usuario o contraseña incorrecta");
        }
        return "conectado !";
    }

    public String logearUsuario(RegistroClienteBody body) throws Exception{
        try {
            conexion.EjecutarQuery("insert into USUARIO values('"+body.userName+"','"+body.password+"','"+body.email+"','C','A','"+body.firstName+"','"+body.secondName+"','"+body.firstLastName+"','"+body.secondLastName+"')");
            conexion.EjecutarQuery("insert into CLIENTE values('"+body.typeID+"',"+body.NID+","+body.numberCell+",'0.0.0.0','"+body.userName+"')");
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
