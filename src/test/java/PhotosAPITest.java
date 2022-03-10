import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient;
import com.google.photos.types.proto.Album;
import com.google.photos.types.proto.MediaItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class PhotosAPITest {

    @Test
    public void initTest() {
        PhotosLibrarySettings settings = getPhotosLibrarySettings();
        try (PhotosLibraryClient photosLibraryClient =
                     PhotosLibraryClient.initialize(settings)) {
            try {
                  // Make a request to list all albums in the user's library
                  // Iterate over all the albums in this list
                  // Pagination is handled automatically
                  InternalPhotosLibraryClient.ListAlbumsPagedResponse response = photosLibraryClient.listAlbums();

                  for (Album album : response.iterateAll()) {
                      System.out.println(album.toString());
                        // Get some properties of an album
                      String id = album.getId();
                      String title = album.getTitle();
                        String productUrl = album.getProductUrl();
                        String coverPhotoBaseUrl = album.getCoverPhotoBaseUrl();
                        // The cover photo media item id field may be empty
                        String coverPhotoMediaItemId = album.getCoverPhotoMediaItemId();
                        boolean isWritable = album.getIsWriteable();
                        long mediaItemsCount = album.getMediaItemsCount();
                  }

            } catch (ApiException e) {
              e.printStackTrace();
            }

        } catch (ApiException | IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void albumTest() {
        String albumId = "ALsuhtYk9cihhlz4gmSgLstXnDvm1c2qlu7Y7H7un_s7l9erlXCE-fguTnLerv4U68XKaT_y8QWu";
        try (PhotosLibraryClient photosLibraryClient = PhotosLibraryClient.initialize(getPhotosLibrarySettings())) {
            try {
                InternalPhotosLibraryClient.SearchMediaItemsPagedResponse response = photosLibraryClient.searchMediaItems(albumId);
                for(MediaItem item : response.iterateAll()) {
                    System.out.println(item);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }


        } catch (ApiException | IOException e) {
            e.printStackTrace();
        }


    }

    private PhotosLibrarySettings getPhotosLibrarySettings() {
        String tokenYahoo = "ya29.a0ARrdaM_qTvpsSPAqWYLstSFOD4xHq1ezgSVIwrECXL3R76QT6UrgGkq8XG2VhDicabP7KyI0NRpMcXFNN7UgfTJA0OTdgG5PcgFuDDwAFeQLSPOkficNF43wHWYMd0ywMAFzeSFr7fe9u3brmRDvPD5Msk4j";
        String tokenGmail = "ya29.a0ARrdaM8AzHvXfuvWcBvNfFP375uD97TEpxWRq7N-UrkSIYTH85b_1M6mmN6t0RdMaIPsLr_b1RX5bVNlpb6EEguCTko6G_BEQsu_WOmZqufYs0CBnd42drrK0zI4gMCpv_FIuaKbIu7tK1ASdQ75e96SNJkp";
        // Set up the Photos Library Client that interacts with the API
        PhotosLibrarySettings settings = null;
        try {
            settings = PhotosLibrarySettings.newBuilder()
                    .setCredentialsProvider(
                            FixedCredentialsProvider.create(
                                    UserCredentials.newBuilder()
                                                    .setClientId("776203731183-dd0ir0ctotn4gem4nj9m8e7jcuf4oa0q.apps.googleusercontent.com")
                                                    .setClientSecret("e0yuK3ItTawGEItPpbQ2-qdu")
                                                    .setAccessToken(new AccessToken(tokenGmail, null))
                                            .build()))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return settings;
    }

}
