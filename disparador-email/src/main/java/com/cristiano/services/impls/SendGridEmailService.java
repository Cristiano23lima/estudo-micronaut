package com.cristiano.services.impls;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cristiano.configuration.SendGridEmailCondition;
import com.cristiano.models.Message;
import com.cristiano.services.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;

@Singleton
@Requires(condition = SendGridEmailCondition.class)
public class SendGridEmailService implements EmailService{

    private static final Logger LOG = LoggerFactory.getLogger(SendGridEmailService.class);

    protected final String apiKey;
    protected final String fromEmail;

    SendGridEmailService(
        @Value("${SENDGRID_APIKEY:none}") String apiKeyEnv,
        @Value("${SENDGRID_FROM_EMAIL:none}") String fromEmailEnv,
        @Value("${sendgrid.apikey:none}") String apikeyProp,
        @Value("${sendgrid.fromemail:none}") String fromEmailProp
    ){
        this.apiKey = apiKeyEnv != null && !apiKeyEnv.equals("none") ? apiKeyEnv : apikeyProp;
        this.fromEmail = fromEmailEnv != null && !fromEmailEnv.equals("none") ? fromEmailEnv : fromEmailProp;
    }

    protected Content contentOfEmail(Message message){
        return new Content("text/plain", message.getBody());
    }

    @Override
    public void send(@NotNull @Valid Message message) {
       Personalization personalization = new Personalization();
       personalization.setSubject(message.getSubject());

       //adding information about the recipient of the email
       Email to = new Email(message.getTo());
       personalization.addTo(to);

        Mail mail = new Mail();
        Email from = new Email();
        from.setEmail(fromEmail);
        mail.from = from;

        mail.addPersonalization(personalization);
        Content content = contentOfEmail(message);
        mail.addContent(content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            
            Response response = sg.api(request);

            if(LOG.isInfoEnabled()){
                LOG.info("Status Code: {}", String.valueOf(response.getStatusCode()));
                LOG.info("Body: {}", response.getBody());
                LOG.info("Headers: {}", response.getHeaders()
                    .keySet()
                    .stream()
                    .map(key -> key.toString() + " = "+response.getHeaders().get(key))
                    .collect(Collectors.joining(", ", "{", "}")));
            }
        }catch(IOException ex){
            LOG.error(ex.getMessage());
        }
    }
    
}
