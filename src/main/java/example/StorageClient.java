package example;

import java.io.InputStream;

public interface StorageClient {

    void uploadBlob(String uploadedFileLocation, String filename);

    void uploadBlob(InputStream inputStream, String filename);

}
