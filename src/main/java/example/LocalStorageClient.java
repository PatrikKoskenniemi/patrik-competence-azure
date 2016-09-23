package example;

import com.microsoft.azure.storage.blob.BlobInputStream;

import java.io.*;
import java.util.List;

public class LocalStorageClient implements StorageClient {

    public LocalStorageClient() {
        System.out.println("LocalStorageClient.LocalStorageClient");
    }


    public void uploadBlob(String fileLocation, String filename) {

        try {
            File destination = new File("/tmp/azuretest/" + filename);
            File source = new File(fileLocation);

            OutputStream outputStream = new FileOutputStream(destination);
            InputStream inputStream = new FileInputStream(source);

            writeToOutput(inputStream,outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadBlob(InputStream inputStream, String filename) {

            File blob = new File("/tmp/inputstreamazure/" + filename);

        try {
            OutputStream OutputStream = new FileOutputStream(blob);

            writeToOutput(inputStream, OutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BlobInputStream downloadBlob(String filename) {

        return null;
    }

    @Override
    public List<String> listBlobs() {
        return null;
    }

    private void writeToOutput(InputStream inputStream, OutputStream outputStream) throws IOException {

        int next = 0;
        next = inputStream.read();
        while (next != -1) {
            outputStream.write(next);
            next = inputStream.read();
        }
        outputStream.close();
    }
}