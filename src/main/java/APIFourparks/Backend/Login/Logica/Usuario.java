package APIFourparks.Backend.Login.Logica;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario implements UserDetails{
    
    @Column(name = "N_NOMBRE_USUARIO")
    @Id
    public String userName;
    @Column(name = "I_ROL")
    public String rol;
    @Column(name = "O_CONTRASEÑA")
    public String pass;
    @Column(name = "O_EMAIL")
    public String email;
    @Column(name = "I_ESTADO")
    public String estado;
    @Column(name = "N_PRIMER_NOMBRE")
    public String primerNombre;
    @Column(name = "N_SEGUNDO_NOMBRE")
    public String segundoNombre;
    @Column(name = "N_PRIMER_APELLIDO")
    public String primerApellido;
    @Column(name = "N_SEGUNDO_APELLIDO")
    public String segundoApellido;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol));
    }
    @Override
    public String getPassword() {
        return pass;
    }
    @Override
    public String getUsername() {
        return userName;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
