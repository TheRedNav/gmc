package src.main.java.br.com.bb.dsc.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.Base64;

@Path("/secured")
public class SecurityResource {

    @GET
    @Path("/everyone")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String helloEveryone() {
        return "Hello, everyone!";
    }

    @GET
    @Path("/user")
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloUser() {
        return "Hello, user!";
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloAdmin() {
        return "Hello, admin!";
    }
    
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Auth")) {
            String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);

            if (values.length == 2) {
                String username = values[0];
                String password = values[1];

                // verificando as credenciais do usuário
                if ("admin".equals(username) && "admin2023".equals(password)) {
                    // Credenciais corretas, acesso permitido
                    return;
                }
            }
        }

        // A autenticação falhou
       requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
       
    }
}
