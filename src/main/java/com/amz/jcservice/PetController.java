package com.amz.jcservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.apache.catalina.Server;
import org.apache.commons.io.IOUtils;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.Serializable;

class PetRequest implements Serializable {
    public String username;
}

@RestController
@EnableAutoConfiguration
public class PetController {
    @Value("${app.secret}")
    private String secret;
    
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/fetchPet", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    Pet getPet(@RequestHeader(value = "x-auth-token") String token, @RequestBody PetRequest input) {
        //By default we dont know the name of the pet
        String found_pet = "";
        try {
            //Get the username from the API input
            found_pet = Pet.fetchPet(input.username);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage() + input.username);
        } finally {
            return Pet.populate(found_pet);
        }
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class BadRequest extends RuntimeException {
        public BadRequest(String exception) {
            super(exception);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class ServerError extends RuntimeException {
        public ServerError(String exception) {
            super(exception);
        }
    }
}
