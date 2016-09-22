package example;


import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;



@Path("/azure")
public class Azure {

    private static final String devstorage = "UseDevelopmentStorage=true";
    // Define the connection-string with your values
    private static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;AccountName=storagepatrik;AccountKey=l0uEp4dcX7UfubLwvKAdsHByNCNUedNxukDyAD0oNC5P1yzdhDhKe6i9a0zR/5do/phv2eNTo65kuGpqNOnAOg==;";


    private StorageClient storageClient = new AzureStorageClient(storageConnectionString);
    //private StorageClient storageClient = new LocalStorage();

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createPicture(@FormDataParam("file") InputStream uploadedInputStream,
                                  @FormDataParam("file") FormDataContentDisposition fileDetail) {

        String uploadedFileLocation = "/home/patrik/Pictures/" + fileDetail.getFileName();

        // save it
        storageClient.uploadBlob(uploadedInputStream, fileDetail.getFileName());

        //storageClient.uploadBlob(uploadedFileLocation, fileDetail.getFileName());

        String output = "File uploaded from : " + uploadedFileLocation;

        return Response.status(200).entity(output).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listPictures() {
        return "This should be a list of all names of the pictures...";
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPicture(@PathParam("name") String name) {
        return "You want the picture with the name: " + name;
    }

}
