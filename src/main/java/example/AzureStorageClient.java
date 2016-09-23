package example;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
    public BlobInputStream downloadBlob(String filename) throws FileNotFoundException, StorageException, URISyntaxException {

            // Loop through each blob item in the container.
            for (ListBlobItem blobItem : container.listBlobs()) {
                // If the item is a blob, not a virtual directory.
                if (blobItem instanceof CloudBlob) {

                    // Download the item and save it to a file with the same name.
                    CloudBlob blob = (CloudBlob) blobItem;
                    if (blob.getName().equals(filename)) {
                        BlobInputStream blobInputStream = blob.openInputStream();
                        //File tempFile = new File("D:\\home\\site\\wwwroot\\" + blob.getName());
                        //blob.download(new FileOutputStream(tempFile));
                        return blobInputStream;
                    }
                }
            }
            throw new FileNotFoundException();
        }

    @Override
    public List<String> listBlobs() {

        List<String> listOfBlobName = new ArrayList<>();
        try
        {
            for (ListBlobItem blobItem : container.listBlobs()) {
                if (blobItem instanceof CloudBlob) {
                    CloudBlob blob = (CloudBlob) blobItem;
                    listOfBlobName.add(blob.getName());
                }
            }
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
        return listOfBlobName;

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
