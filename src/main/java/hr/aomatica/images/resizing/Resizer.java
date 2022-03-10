package hr.aomatica.images.resizing;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Resizer {

    /**
     * Resizes given image
     * @param input
     * @return
     */
    BufferedImage resize(BufferedImage input) throws IOException;
}
