package APIFourparks.Backend.Login.Services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import APIFourparks.Backend.Login.Controladores.RequestBody.RegistroClienteBody;
import APIFourparks.Backend.Login.Logica.Conexion;
import APIFourparks.Backend.Login.Services.JwtUtils;

public class ConexionService {
    private static ConexionService servicio;
    private Conexion conexion;

    public JwtUtils jwtUtils;

    private ConexionService() {
        conexion = Conexion.obtenerConexion();
        jwtUtils = new JwtUtils();
    }

    public static ConexionService obtenerServicio() {
        if (servicio == null) {
            servicio = new ConexionService();
        }
        return servicio;
    }

    public Map<String,Object> logearUsuario(String user, String pass) throws Exception{
        java.util.List<Map<String, Object>> resultado = conexion.SelectQuery("select * from USUARIO where N_NOMBRE_USUARIO='"+user+"' and O_CONTRASEÑA='"+pass+"'");
        if(resultado.size() == 0){
            resultado = conexion.SelectQuery("select * from USUARIO where N_NOMBRE_USUARIO='"+user+"' ");
            if(resultado.size()!= 0){
                conexion.EjecutarQuery("UPDATE USUARIO SET N_INTENTOS_FALLIDOS = N_INTENTOS_FALLIDOS+1 WHERE N_NOMBRE_USUARIO ='"+user+"'");
                if (((java.math.BigDecimal)resultado.get(0).get("N_INTENTOS_FALLIDOS")).intValue() >= 2){
                    conexion.EjecutarQuery("UPDATE USUARIO SET I_ESTADO = 'B' WHERE N_NOMBRE_USUARIO ='"+user+"'");
                    throw new Exception("Su cuenta ha sido bloqueada, por favor comuniquese con un Administrador");
                }
            }
            throw new Exception("Usuario o contraseña incorrecta");
        }else if(resultado.get(0).get("I_ESTADO").equals("B")){
            throw new Exception("Su cuenta ha sido bloqueada, por favor comuniquese con un Administrador");
        }
        conexion.EjecutarQuery("UPDATE USUARIO SET N_INTENTOS_FALLIDOS = 0 WHERE N_NOMBRE_USUARIO ='"+user+"'");
        return Map.of("message","conectado!","token",jwtUtils.generateToken(user),"rol",resultado.get(0).get("I_ROL"));
    }

    public Map<String,Object> registrarUsuario(RegistroClienteBody body) throws Exception{
        try {
            conexion.EjecutarQuery("insert into USUARIO values('"+body.userName+"','"+body.password+"','"+body.email+"','C','A','"+body.firstName+"','"+body.secondName+"','"+body.firstLastName+"','"+body.secondLastName+"',0)");
            conexion.EjecutarQuery("insert into CLIENTE values('"+body.typeID+"',"+body.NID+","+body.numberCell+",'"+body.userName+"',0)");
            return Map.of("message","Registro Exitoso!","token",jwtUtils.generateToken(body.userName));
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

    public String cambiarContrasena(String nameUser, String password) {
        conexion.EjecutarQuery("UPDATE USUARIO SET O_CONTRASEÑA ='"+password+"' WHERE N_NOMBRE_USUARIO ='"+ nameUser+"'");
        return "se ha actualizado la contraseña exitosamente";
    }

}
