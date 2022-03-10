package hr.aomatica.images;

public class ImageSaverException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ImageSaverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageSaverException(String message) {
        super(message);
    }

}
