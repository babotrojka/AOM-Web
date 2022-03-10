package hr.aomatica.images.resizing;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ThumnbnailatorResizer implements Resizer{

    @Override
    public BufferedImage resize(BufferedImage input) throws IOException {
        return Thumbnails.of(input).size(640, 480).asBufferedImage();
    }
}
