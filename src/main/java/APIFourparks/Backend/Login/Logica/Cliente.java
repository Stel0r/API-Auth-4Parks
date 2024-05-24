package APIFourparks.Backend.Login.Logica;

import java.math.BigInteger;

import APIFourparks.Backend.Login.Controladores.Repositorios.ClienteID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(ClienteID.class)
@Table(name = "CLIENTE")
public class Cliente {

@Id
@Column(name = "I_TIPO_DOC")
public String tipoDocumento;
@Id
@Column(name = "K_NUM_DOCUMENTO")
public BigInteger numDoc;
@Column(name = "Q_NUM_CELULAR")
public BigInteger numCel;
@Column(name = "N_NOMBRE_USUARIO")
public String nomUsuario;
@Column(name = "N_PTS_FIDELIZACIÓN")
public int ptsFidelizacion;



    
}
