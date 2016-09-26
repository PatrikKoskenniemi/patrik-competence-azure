package example;


import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobInputStream;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.org.apache.regexp.internal.RE;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URISyntaxException;
import java.util.List;


@Path("/azure")
public class Azure {

    private static final String devstorage = "UseDevelopmentStorage=true";
    // Define the connection-string with your values
    private static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;AccountName=patrik1337azure;AccountKey=JN2O6LsroB5rWSDXc5p3e4X423nvZpAUk6HDasg2ZtLR9jQ+LqCIVSolUTfOn+APBUBPTlB4UTcbfpegeNSF5Q==;";

    private StorageClient storageClient = new AzureStorageClient(storageConnectionString);
    //private StorageClient storageClient = new LocalStorageClient();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createPicture(@FormDataParam("file") InputStream uploadedInputStream,
                                  @FormDataParam("file") FormDataContentDisposition fileDetail) {

        // save it
        storageClient.uploadBlob(uploadedInputStream, fileDetail.getFileName());

        String output = "File uploaded!";

        return Response.status(200).entity(output).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listPictures() {
        List<String> list = storageClient.listBlobs();
        return list.toString();
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPicture(@PathParam("name") String name, @QueryParam("size") Size size) {

        InputStream inputStream = null;
        try {
            inputStream = storageClient.downloadBlob(name, size);
        } catch (FileNotFoundException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(inputStream, MediaType.APPLICATION_OCTET_STREAM).build();

    }
}
