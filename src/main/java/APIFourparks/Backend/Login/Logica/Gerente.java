package APIFourparks.Backend.Login.Logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GERENTE")
public class Gerente {
    
    @Id
    @Column(name = "K_COD_GERENTE")
    public String idGerente;
    @Column(name = "N_NOMBRE_USUARIO")
    public String user;

}
