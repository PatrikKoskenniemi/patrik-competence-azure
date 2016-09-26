package example;


import java.io.*;
import java.util.List;

public class LocalStorageClient implements StorageClient {

    private final static String defaultLocation = "/tmp/AzureLocalStorage/";

    public LocalStorageClient() {
        System.out.println("LocalStorageClient.LocalStorageClient");
    }

    @Override
    public void uploadBlob(InputStream inputStream, String filename) {

            File blob = new File(defaultLocation + filename);

        try {
            OutputStream OutputStream = new FileOutputStream(blob);

            writeToOutput(inputStream, OutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream downloadBlob(String filename) throws FileNotFoundException {

        File blob = new File(defaultLocation + filename);
        InputStream inputStream = new FileInputStream(blob);

        return inputStream;
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
