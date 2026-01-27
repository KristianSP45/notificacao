package com.kristian.notificacao.business;

import com.kristian.notificacao.business.dto.TarefasDTO;
import com.kristian.notificacao.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    //Ele é um objeto do Spring que:
    //sabe conectar no servidor de e-mail (SMTP)
    //sabe autenticar
    //sabe enviar o e-mail
    private final TemplateEngine templateEngine;//TemplateEngine = montador de HTML dinâmico
    //Ele pega: um arquivo HTML(dados)
    //e transforma em um HTML final

    @Value("${envio.email.remetente}")//@Value = Puxa valor do application.properties
    //Configurações via properties: Isso vem do application.yml ou properties.
    private String remetente;

    @Value("${envio.email.nomeRemetente}")
    private  String nomeRemetente;

    public void enviaEmail(TarefasDTO dto){
        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();//Cria o objeto do e-mail vazio.
            //MimeMessage = o e-mail em branco
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());
            //MimeMessageHelper cria uma ferramenta pra montar esse e-mail = caneta + régua + corretor
            //Parâmetros:
            //1 mensagem = o e-mail que ele vai ajudar a montar
            //2 true > permite HTML / anexos = HTML, anexo, multipart
            //3 UTF-8 > evita bug com acento

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));//helper.setFrom(...) = Define quem está enviando.
            mimeMessageHelper.setTo(InternetAddress.parse(dto.getEmailUsuario()));//helper.setTo(...) = Define pra quem vai.
            mimeMessageHelper.setSubject("Notificação de Tarefa");//helper.setSubject(...) = Define o assunto.

            Context context = new Context();//Context = mapa de variáveis pro HTML.
            context.setVariable("nomeTarefa", dto.getNomeTarefa());
            context.setVariable("dataEvento", dto.getDataEvento());
            context.setVariable("descricao", dto.getDescricao());
            String template = templateEngine.process("notificacao", context);//Isso: Pega notificacao.html + dados > HTML final pronto.
            mimeMessageHelper.setText(template, true);//true > indica que é HTML e coloca o HTML dentro do e-mail.
            javaMailSender.send(mensagem);//Aqui o e-mail realmente sai / Entrega o e-mail pro servidor SMTP.
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar o email "+e.getCause());
        }
    }
}
