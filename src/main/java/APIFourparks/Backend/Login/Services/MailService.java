package APIFourparks.Backend.Login.Services;

import org.springframework.stereotype.Service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class MailService {
    private static MailjetClient client;
    private static Dotenv dotenv;

    private MailService() {
        
    }

    public static MailService obtenerServicio(){
        if (client == null) {

            dotenv = Dotenv.load();
            ClientOptions options = ClientOptions.builder()
                    .apiKey(dotenv.get("MAIL_USER"))
                    .apiSecretKey(System.getenv("MAIL_PASSWORD"))
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
                .customID("custom-id-value")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        SendEmailsResponse response = request.sendWith(client);
    }

}
