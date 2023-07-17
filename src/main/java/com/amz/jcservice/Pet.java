package com.amz.jcservice;

import org.apache.catalina.Server;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Pet {
    public String pet;

    public Pet(String pet) {
        this.pet = pet;
    }

    public static String fetchPet(String username) {
        Statement stmt = null;
        String pet = "";
        try {
            Connection cxn = Postgres.connection();
            stmt = cxn.createStatement();
            // Get the userdetails for the username sent
            String query = "select * from users where username = '"+username+"'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                //Parse and find the pet that username has
                pet = rs.getString("pet");
            }
            System.out.println("Found this pet from DB "+ pet);
            cxn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            return pet;
        }
    }

    public static Pet populate(String pet) {
        Pet found_pet = new Pet(pet);
        return found_pet;
    }
}
