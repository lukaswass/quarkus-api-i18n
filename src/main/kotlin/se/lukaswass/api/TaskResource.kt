package se.lukaswass.api

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import se.lukaswass.i18n.L10nBundle
import se.lukaswass.i18n.TaskMessages

@Path("/tasks")
class TaskResource {
    private val messages by L10nBundle.of<TaskMessages>()

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    fun getTaskMessage(name: String): Response {
        return Response.status(200).entity(messages.sayTask(name)).build()
    }

    @GET
    @Path("")
    @Produces(MediaType.TEXT_PLAIN)
    fun getNoTaskMessage(name: String): Response {
        return Response.status(400).entity(messages.error()).build()
    }
}