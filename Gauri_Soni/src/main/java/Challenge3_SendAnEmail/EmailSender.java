package Challenge3_SendAnEmail;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.PasswordAuthentication;
import java.io.File;
import java.util.Properties;

public class EmailSender {

    public static void main(String[] args) {

        // YOUR personal email for testing
        final String fromEmail = "sonigauri1208@gmail.com";
        final String appPassword = "XXXX XXXX XXXX XXXX";

        // SMTP configuration
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, appPassword);
                    }
                });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("hr@ignitershub.com")
            );

            message.setSubject("Challenge 3 Completed");

            // Email body
            BodyPart messageBody = new MimeBodyPart();
            messageBody.setText(
                    "Name: Gauri Soni\n" +
                            "Highest Qualification: MCA\n" +
                            "Year: 2025\n"
            );

            // Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File("H:/interns-assignments/Gauri_Soni/image.jpg");   // PNG/JPG/JPEG
            attachmentPart.attachFile(file);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBody);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
