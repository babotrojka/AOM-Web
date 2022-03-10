package hr.aomatica.email;

public interface MailSender {
    void send(String recipient, String title, String message);
}
