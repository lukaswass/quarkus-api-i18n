package se.lukaswass.api

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import se.lukaswass.i18n.L10nBundle
import se.lukaswass.i18n.UserMessages

@Path("/users")
class UserResource {
    private val messages by L10nBundle.of<UserMessages>()

    @GET
    @Path("hello/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    fun getHelloMessage(name: String): Response {
        return Response.status(200).entity(messages.hello(name)).build()
    }

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun getHelloMessageNoName(name: String): Response {
        return Response.status(400).entity(messages.errorNeedName()).build()
    }

    @GET
    @Path("broken")
    @Produces(MediaType.TEXT_PLAIN)
    fun getBrokenMessage(): Response {
        return Response.status(500).entity(messages.error()).build()
    }
}