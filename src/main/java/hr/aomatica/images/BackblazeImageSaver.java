package hr.aomatica.images;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentHandlers.B2ContentOutputStreamWriter;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.contentSources.B2ContentTypes;
import com.backblaze.b2.client.contentSources.B2FileContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2Bucket;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import hr.aomatica.images.resizing.Resizer;
import hr.aomatica.images.resizing.ThumnbnailatorResizer;
import hr.aomatica.util.properties.particular.LoadBackblazeProperties;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BackblazeImageSaver implements ImageSaver{

    private final String userAgent = "BackblazeImageServlet";
    private B2StorageClient client;
    private boolean initCheck = false;
    Properties backProperties;

    @Override
    public boolean isInit() {
        return initCheck;
    }

    @Override
    public void init(ServletContext servletContext) {
        backProperties = new LoadBackblazeProperties().loadThisProperties(servletContext);
        client = B2StorageClientFactory.createDefaultFactory().create(backProperties.getProperty("app_key_id"), backProperties.getProperty("app_key"), userAgent);
        initCheck = true;
    }

    @Override
    public void loadImage(String id, OutputStream os) throws ImageSaverException{
        if(!initCheck) throw new ImageSaverException("You must init image saver first");

        B2ContentOutputStreamWriter outputStreamWriter = createOsWriter(os);

        try {
            client.downloadById(id, outputStreamWriter);
        } catch (B2Exception e) {
            throw new ImageSaverException(e.getMessage());
        }
    }

    @Override
    public void loadImageWithBucketAndName(String bucketName, String name, OutputStream os) throws ImageSaverException {
        if(!initCheck) throw new ImageSaverException("You must init image saver first");

        B2ContentOutputStreamWriter outputStreamWriter = createOsWriter(os);
        try {
            client.downloadByName(bucketName, name, outputStreamWriter);
        } catch (B2Exception e) {
            throw new ImageSaverException(e.getMessage());
        }
    }

    @Override
    public void loadImageWithBucketAndName(BucketNames b, String name, OutputStream os) throws ImageSaverException {
        if(!initCheck) throw new ImageSaverException("You must init image saver first");

        loadImageWithBucketAndName(getBucketNameFromEnum(b), name, os);
    }

    @Override
    public List<String> getFilenamesOfBucket(BucketNames name) throws ImageSaverException {
        B2Bucket files = null;
        try {
            files = client.getBucketOrNullByName(getBucketNameFromEnum(name));
        } catch (B2Exception e) {
            e.printStackTrace();
        }

        if(files == null) {
            return null;
        }

        List<String> filenames = new ArrayList<>();
        try {
            client.fileNames(files.getBucketId()).forEach(bf2v -> filenames.add(backProperties.getProperty("backblaze_url") + bf2v.getFileId()));
        } catch (B2Exception e) {
            e.printStackTrace();
        }

        return filenames;
    }

    @Override
    public String saveImage(InputStream is, BucketIDs b, String filename) throws IOException, ImageSaverException {
        if(!initCheck) throw new ImageSaverException("You must init image saver first");
        Resizer r = new ThumnbnailatorResizer();
        BufferedImage resized = r.resize(ImageIO.read(is));

        String bId = null;
        switch (b) {
            case NOVOST -> bId = backProperties.getProperty("novosti_id");
            case HOMEPAGE -> bId = backProperties.getProperty("homepage_id");
            case SKOLICA -> bId = backProperties.getProperty("skolica_id");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resized, filename.substring(filename.lastIndexOf(".") + 1), baos);

        B2FileVersion fv;
        try {
            fv = client.uploadSmallFile(B2UploadFileRequest.builder(
                    bId,
                    filename,
                    B2ContentTypes.B2_AUTO,
                    B2ByteArrayContentSource.build(baos.toByteArray())
            ).build());
        } catch (B2Exception e) {
            throw new ImageSaverException(e.getMessage());
        }

        return fv.getFileId();
    }

    @Override
    public void deleteImage(String id) throws ImageSaverException {
        if(!initCheck) throw new ImageSaverException("You must init image saver first");

        try {
            String filename = client.getFileInfo(id).getFileName();
            client.deleteFileVersion(filename, id);
        } catch (B2Exception e) {
            throw new ImageSaverException(e.getMessage());
        }
    }

    private String getBucketNameFromEnum(BucketNames name) {
        String bucketName = null;

        switch (name) {
            case NOVOST -> bucketName = backProperties.getProperty("novosti");
            case SKOLICA -> bucketName = backProperties.getProperty("skolica");
            case HOMEPAGE -> bucketName = backProperties.getProperty("homepage");
        }

        return bucketName;
    }

    private B2ContentOutputStreamWriter createOsWriter(OutputStream os) {
        return B2ContentOutputStreamWriter.builder(new B2ContentOutputStreamWriter.Helper() {
            @Override
            public OutputStream createOutputStream() throws IOException {
                return os;
            }
        }).build();
    }
}
