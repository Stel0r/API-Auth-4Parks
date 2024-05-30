package APIFourparks.Backend.Login.Services;

import org.springframework.stereotype.Service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;


@Service
public class MailService {
    private static MailjetClient client;

    private MailService() {
        
    }

    public static MailService obtenerServicio(){
        if (client == null) {
            ClientOptions options = ClientOptions.builder()
                    .apiKey("4fd36d19df920b708f9068e00c9a2b98")
                    .apiSecretKey("3a5b9ca6d5389e2e2c9ce17489e7848b")
                    .build();

            client = new MailjetClient(options);
        }
        return new MailService();
    }

    public void mandarCorreonuevoRegistro(String correoDestino,String usuario,String contrasena) throws MailjetException{
        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(correoDestino, "stanislav"))
                .from(new SendContact("diego.felipe.gamez@gmail.com", "Mailjet integration test"))
                .htmlPart("<h1>Bienvenido a FourParks</h1> <p>Nos enorgullece recibirte en nuestro equipo su nueva contraseña y usuario los encontrara a continuacion</p><p>Usuario:"+usuario+"</p><p>Contraseña:"+contrasena+"</p>")
                .subject("Bienvenido a Fourparks !") 
                .header("test-header-key", "test-value")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        SendEmailsResponse response = request.sendWith(client);
    }

    public void mandarCorreoBloqueo(String correoDestino,String usuario) throws MailjetException{
        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(correoDestino, "stanislav"))
                .from(new SendContact("diego.felipe.gamez@gmail.com", "Mailjet integration test"))
                .htmlPart("<h1>Nuevo Usuario Bloqueado</h1> <p>se informa que el usuario "+usuario+" ha sido bloqueado de la plataforma four-parks, Puede desbloquear el usuario ingresando al area administrativa y desbloqueandolo</p>")
                .subject("Alerta de bloqueo de usuario") 
                .header("test-header-key", "test-value")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        SendEmailsResponse response = request.sendWith(client);
    }



}
