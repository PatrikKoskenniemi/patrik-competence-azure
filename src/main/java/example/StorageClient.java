package example;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobInputStream;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public interface StorageClient {

    void uploadBlob(String uploadedFileLocation, String filename);

    void uploadBlob(InputStream inputStream, String filename);

    BlobInputStream downloadBlob(String filename) throws FileNotFoundException, StorageException, URISyntaxException;

    List<String> listBlobs();
}
