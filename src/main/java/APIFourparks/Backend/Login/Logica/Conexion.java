package APIFourparks.Backend.Login.Logica;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public class Conexion {
    private JdbcTemplate template;
    private static Conexion conexion;

    private Conexion() {
        DriverManagerDataSource data = new DriverManagerDataSource();
        data.setDriverClassName("org.mariadb.jdbc.Driver");
        data.setUrl("jdbc:mariadb://iu51mf0q32fkhfpl.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/qs58vsedzpr4mma5");
        data.setUsername("kyjapvqoeyewm9ik");
        data.setPassword("mloxu4drkuuqfa3k");

        this.template = new JdbcTemplate(data);
    }

    public static Conexion obtenerConexion(){
        if(conexion == null){
            conexion = new Conexion();
        }
        return conexion;
    }
    @Transactional
    public List<Map<String, Object>> SelectQuery(String sql) {
        try {
            List<Map<String, Object>> result = this.template.queryForList(sql);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }
    @Transactional
    public void EjecutarQuery(String sql) {
        try {
            this.template.execute(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }
}
