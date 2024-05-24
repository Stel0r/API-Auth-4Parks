package APIFourparks.Backend.Login.Logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administrador")
public class Administrador {

    @Id
    @Column(name = "K_COD_ADMINISTRADOR")
    public String idAdmin;
    @Column(name = "N_NOMBRE_USUARIO")
    public String user;

    
}
