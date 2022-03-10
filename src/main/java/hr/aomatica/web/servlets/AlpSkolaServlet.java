package hr.aomatica.web.servlets;

import com.backblaze.b2.client.B2ListFilesIterable;
import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2Bucket;
import com.backblaze.b2.client.structures.B2FileVersion;
import hr.aomatica.images.BucketNames;
import hr.aomatica.images.ImageSaver;
import hr.aomatica.images.ImageSaverProvider;
import hr.aomatica.util.properties.particular.LoadBackblazeProperties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet ("/skola/")
public class AlpSkolaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("active", "skola");

        ImageSaver saver = ImageSaverProvider.getImageSaver(req.getServletContext());

        List<String> filenames = saver.getFilenamesOfBucket(BucketNames.SKOLICA);

        req.setAttribute("mainSlika", filenames.get(0));
        req.setAttribute("skolaSlike", filenames.subList(1, filenames.size()));
        req.setAttribute("title", "Alpinisticka skola");
        req.getRequestDispatcher("/WEB-INF/pages/alp_skola.jsp").forward(req, resp);
    }
}
