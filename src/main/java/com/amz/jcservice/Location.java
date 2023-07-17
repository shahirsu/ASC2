package com.amz.jcservice;

import org.apache.catalina.Server;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.UUID;
import com.amz.jcservice.Constants;


public class Location {
    public ArrayList<String> locations;

    public Location(ArrayList<String> locations) {
        this.locations = locations;
    }

    public static String getLocation(String pet) {
        String http_builder = Constants.protocol 
                                + "://" 
                                + Constants.host
                                + "/";
        if(Arrays.asList(Constants.allpets).contains(pet)){
            http_builder += pet + Constants.location_extension;
        }
        else if(Arrays.asList(Constants.hiddenToTheWorld).contains(pet)){
            http_builder += "error"+Constants.error_extension;
        }
        else{
            http_builder = pet;
        }
        System.out.println("\n\n Built this URL: "+http_builder);
        return http_builder;
    }

    public static Location populate(String location) {
        ArrayList<String> location_builder = new ArrayList<String>();
        location_builder.add(0, UUID.randomUUID().toString());
        location_builder.add(1, location);
        Location result_location = new Location(location_builder);
        return result_location;
    }
}