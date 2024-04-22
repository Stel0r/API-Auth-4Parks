package APIFourparks.Backend.Login.Services;

import APIFourparks.Backend.Login.Logica.Conexion;



public class ConexionService {
    private static ConexionService servicio;

    private ConexionService() {
    }

    public static ConexionService obtenerServicio() {
        if (servicio == null) {
            servicio = new ConexionService();
        }
        return servicio;
    }

    public String logearUsuario(String user, String pass){
        Conexion conexion = new Conexion(user ,pass);
            conexion.EjecutarQuery("select 1");
            return "conectado !";
    }

}
