package APIFourparks.Backend.Login.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import APIFourparks.Backend.Login.Controladores.RequestBody.RegistroClienteBody;
import APIFourparks.Backend.Login.Logica.Conexion;
import APIFourparks.Backend.Login.Services.JwtUtils;

public class ConexionService {
    private static ConexionService servicio;
    private Conexion conexion;

    public JwtUtils jwtUtils;

    private MailService mailService = MailService.obtenerServicio();

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
                    List<Map<String,Object>> adminRes =  this.conexion.SelectQuery("select O_EMAIL from USUARIO where I_ROL = 'A'");
                    if(adminRes.size() != 0){
                        mailService.mandarCorreoBloqueo(adminRes.get(0).get("O_EMAIL").toString(), user);
                    }
                    throw new Exception("por seguridad su cuenta ha sido bloqueada, el administrador sera notificado.");
                }
            }
            throw new Exception("Usuario o contraseña incorrecta");
        }else if(resultado.get(0).get("I_ESTADO").equals("B")){
            throw new Exception("Su cuenta esta bloqueada, por favor comuniquese con el Administrador");
        }
        conexion.EjecutarQuery("UPDATE USUARIO SET N_INTENTOS_FALLIDOS = 0 WHERE N_NOMBRE_USUARIO ='"+user+"'");
        HashMap<String,Object> response = new HashMap<>(Map.of("message","conectado!","token",jwtUtils.generateToken(user),"rol",resultado.get(0).get("I_ROL")));
        
        if (resultado.get(0).get("I_ROL").equals("G")){
            List<Map<String,Object>> gerente = this.conexion.SelectQuery("select * from LOGIN_GERENTE where N_NOMBRE_USUARIO = '"+user+"'");
            System.out.println(gerente.size());
            if (gerente.size() != 0){
                response.put("firstlogin",true);
            }else{
                System.out.println(response);
                response.put("firstlogin",false);
            }
        }
        return response;
    }

    public void asingnarParqueadero(String codGerente,String codParqueadero){
        conexion.EjecutarQuery("UPDATE Parqueadero SET K_COD_GERENTE = '"+codGerente+"' WHERE K_COD_PARQUEADERO = '"+codParqueadero+"'");
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
        conexion.EjecutarQuery("DELETE FROM LOGIN_GERENTE WHERE N_NOMBRE_USUARIO = '"+nameUser+"'");
        return "se ha actualizado la contraseña exitosamente";
    }

}
