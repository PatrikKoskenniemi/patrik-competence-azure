package example;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

import java.io.*;
import java.net.URISyntaxException;

public class AzureStorageClient implements StorageClient {

    CloudBlobClient blobClient;
    CloudBlobContainer container;

    public AzureStorageClient(String storageString) {

        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageString);

            // Create the blob client.
            blobClient = storageAccount.createCloudBlobClient();

            // Get a reference to a container.
            // The container name must be lower case
            container = blobClient.getContainerReference("mycontainer");

            // Create the container if it does not exist.
            container.createIfNotExists();

            // Create a permissions object.
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Include public access in the permissions object.
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

            // Set the permissions on the container.
            container.uploadPermissions(containerPermissions);
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
    @Override
    public void uploadBlob(InputStream inputStream, String filename) {

        try {
            CloudBlockBlob blob = container.getBlockBlobReference(filename); // assuming container was already created

            BlobOutputStream blobOutputStream = blob.openOutputStream();

            int next = 0;
            next = inputStream.read();
            while (next != -1) {
                blobOutputStream.write(next);
                next = inputStream.read();
            }
            blobOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void uploadBlob(String uploadedFileLocation, String filename) {
        try
        {
            // Create or overwrite the "myimage.jpg" blob with contents from a local file.
            CloudBlockBlob blob = container.getBlockBlobReference(filename);
            File source = new File(uploadedFileLocation);
            blob.upload(new FileInputStream(source), source.length());

        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
}
