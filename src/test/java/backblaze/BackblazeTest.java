package backblaze;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import com.backblaze.b2.client.contentHandlers.B2ContentMemoryWriter;
import com.backblaze.b2.client.contentHandlers.B2ContentOutputStreamWriter;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2Bucket;
import com.backblaze.b2.client.structures.B2FileVersion;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BackblazeTest {

    @Test
    public void initTest() {
        String appKeyId = "002b9f32a1ee4980000000001";
        String appKey = "K002AnSuTNrC/UjgglSZ8g5wj7C7YHQ";
        String userAgent = "BackblazeTest";


        B2StorageClient client = B2StorageClientFactory
                                    .createDefaultFactory()
                                    .create(appKeyId, appKey, userAgent);

        try {
            List<B2Bucket> buckets = client.listBuckets().getBuckets();
            B2ContentMemoryWriter memoryWriter = B2ContentMemoryWriter.build();
            for(B2Bucket bucket : buckets) {
                System.out.println(bucket.getBucketName());
                System.out.println(bucket.getBucketId());
                for(B2FileVersion filename : client.fileNames(bucket.getBucketId())) {
                    System.out.println("\t" + filename.getFileName());
                }
            }




        } catch (B2Exception e) {
            e.printStackTrace();
        }
    }
}
