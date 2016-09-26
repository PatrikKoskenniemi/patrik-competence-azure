package example;

import com.microsoft.azure.storage.StorageException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public interface StorageClient {

    void uploadBlob(InputStream inputStream, String filename);

    InputStream downloadBlob(String filename, Size size) throws FileNotFoundException, StorageException, URISyntaxException;

    List<String> listBlobs();
}
