package hr.aomatica.web.servlets.backblaze;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentHandlers.B2ContentMemoryWriter;
import com.backblaze.b2.client.contentHandlers.B2ContentOutputStreamWriter;
import com.backblaze.b2.client.exceptions.B2Exception;
import hr.aomatica.images.BackblazeImageSaver;
import hr.aomatica.images.ImageSaver;
import hr.aomatica.images.ImageSaverException;
import hr.aomatica.images.ImageSaverProvider;
import hr.aomatica.util.properties.particular.LoadBackblazeProperties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Servlet connected with serving images
 */
@WebServlet("/backblaze/*")
public class BackblazeImageServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getPathInfo().substring(1);

        long numberOfSlashes = p.chars().filter(c -> c == '/').count();
        if(numberOfSlashes > 1) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        ImageSaver imageSaver = ImageSaverProvider.getImageSaver(req.getServletContext());

        if(numberOfSlashes == 1) {
            String bucketName = p.substring(0, p.indexOf("/"));
            String filename = p.substring(p.indexOf("/") + 1);

            try {
                imageSaver.loadImageWithBucketAndName(bucketName, filename, resp.getOutputStream());
            } catch (ImageSaverException e) {
                resp.sendRedirect(req.getContextPath()); //todo
                e.printStackTrace();
            }
        } else {
            try {
                imageSaver.loadImage(p, resp.getOutputStream());
            } catch (ImageSaverException e) {
                resp.sendRedirect(req.getContextPath()); //todo
                e.printStackTrace();
            }
        }

        resp.getOutputStream().close();
    }

}
