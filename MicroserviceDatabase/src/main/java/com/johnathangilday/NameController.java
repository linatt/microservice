package com.johnathangilday;


import com.mongodb.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ParamConverter;
import java.net.UnknownHostException;

@Path("/name")
@Produces(MediaType.TEXT_PLAIN)
public class NameController {

    @POST
    @Path("/register")
    public String register(@FormParam("name") String name, @FormParam("url") String url) throws UnknownHostException {
   // public String register (@QueryParam("name") String name, @QueryParam("url") String url) throws UnknownHostException {
       // public String register(@PathParam("name") final String name, @PathParam("url") final String url) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB registry = mongoClient.getDB("registry");

        DBCollection services = registry.getCollection("services");
        DBObject query = new BasicDBObject();
        query.put("name", name);
        DBObject update = new BasicDBObject();
        update.put("name", name);
        update.put("url", url);
        DBObject res = services.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
        return "cool";
    }

    @GET
    @Path("/{name}")
    public String getNameById(@PathParam("name") final String name) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB registry = mongoClient.getDB("registry");

        DBCollection services = registry.getCollection("services");
        DBObject query = new BasicDBObject();
        query.put("name", name);
        DBObject res = services.findOne(query);
        return res.get("url").toString();
    }
}
