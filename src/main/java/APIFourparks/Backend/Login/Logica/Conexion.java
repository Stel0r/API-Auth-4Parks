package APIFourparks.Backend.Login.Logica;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.List;
import java.util.Map;


public class Conexion {
    private JdbcTemplate template;

    public Conexion(String user, String pass) {
        DriverManagerDataSource data = new DriverManagerDataSource();
        data.setDriverClassName("com.mysql.cj.jdbc.Driver");
        data.setUrl("jdbc:mysql://localhost:3306/cliente_db");
        data.setUsername(user);
        data.setPassword(pass);

        this.template = new JdbcTemplate(data);
    }

    public List<Map<String, Object>> SelectQuery(String sql) {
        try {
            List<Map<String, Object>> result = this.template.queryForList(sql);
            return result;
        } catch (Exception e) {
            throw e;
        }

    }

    public void EjecutarQuery(String sql) {
        try {
            this.template.execute(sql);
        } catch (Exception e) {
            throw e;
        }

    }
}
