package hr.aomatica.images;

import javax.servlet.ServletContext;

public class ImageSaverProvider {

    private static ImageSaver imageSaver = new BackblazeImageSaver();

    public static ImageSaver getImageSaver(ServletContext context) {
        if(!imageSaver.isInit()) imageSaver.init(context);
        return imageSaver;
    }
}
