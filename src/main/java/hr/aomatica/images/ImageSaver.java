package hr.aomatica.images;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ImageSaver {

    /**
     * Returns whether this saver is init or not
     * @return
     */
    boolean isInit();

    /**
     * Init image saver
     * @param servletContext
     */
    void init(ServletContext servletContext);

    /**
     * Loads image
     * @param id
     * @param os
     */
    void loadImage(String id, OutputStream os) throws ImageSaverException;

    /**
     * Loads image from bucket and name
     * @param bucket
     * @param name
     * @param os
     * @throws ImageSaverException
     */
    void loadImageWithBucketAndName(String bucket, String name, OutputStream os) throws ImageSaverException;

    /**
     * Loads image from bucket enum and name
     * @param b
     * @param name
     * @param os
     * @throws ImageSaverException
     */
    void loadImageWithBucketAndName(BucketNames b, String name, OutputStream os) throws ImageSaverException;

    /**
     * Returns list of filenames in bucket name
     * @param name
     * @return
     * @throws ImageSaverException
     */
    List<String> getFilenamesOfBucket(BucketNames name) throws ImageSaverException;

    /**
     * Saves image
     * @param is
     * @return
     */
    String saveImage(InputStream is, BucketIDs b, String filename) throws IOException, ImageSaverException;

    /**
     * Deletes image
     * @param id
     * @throws ImageSaverException
     */
    void deleteImage(String id) throws ImageSaverException;
}
