package imgscalr;

import org.imgscalr.Scalr;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;

public class ImgScalrTest {

    @Test
    public void initTest() {
        JFileChooser jfc = new JFileChooser();
        int returnVal = jfc.showOpenDialog(null);

        if(returnVal == JFileChooser.CANCEL_OPTION) return;

        BufferedImage image = null;
        try {
            image = ImageIO.read(jfc.getSelectedFile());
            System.out.println("Velicina na pocetku je (MB): " + Files.size(jfc.getSelectedFile().toPath()) / 1024 / 1024);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        BufferedImage result = Scalr.resize(image, 1920);
        try {
            ImageIO.write(result, "jpg", jfc.getSelectedFile());
            System.out.println("Velicina na kraju je (MB): " + Files.size(jfc.getSelectedFile().toPath()) / 1024 / 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
