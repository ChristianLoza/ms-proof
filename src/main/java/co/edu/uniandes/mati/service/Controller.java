package co.edu.uniandes.mati.service;


import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("graphql")
public class Controller {

    @Inject
    ClientApi clientApi;


    @GET
    @Blocking
    public Response executeClient() {
        System.out.println(clientApi.getQuery("Java").toString());
        return Response.ok(clientApi.getQuery("Java")).build();
    }
}
