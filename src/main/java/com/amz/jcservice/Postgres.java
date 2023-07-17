package com.amz.jcservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

public class Postgres {

    public static Connection connection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = new StringBuilder()
                    .append("jdbc:postgresql://")
                    .append(System.getenv("PGHOST"))
                    .append("/")
                    .append(System.getenv("PGDATABASE")).toString();
            return DriverManager.getConnection(url,
                    System.getenv("PGUSER"), System.getenv("PGPASSWORD"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(1);
        }
        return null;
    }
    public static void setup(){
        try {
            System.out.println("Setting up Database...");
            Connection c = connection();
            Statement stmt = c.createStatement();

            // Create Schema
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users(user_id VARCHAR (36) PRIMARY KEY, username VARCHAR (50) UNIQUE NOT NULL, password VARCHAR (50) NOT NULL, pet VARCHAR (50), last_login TIMESTAMP)");

            // Clean up any existing data
            stmt.executeUpdate("DELETE FROM users");

            // Insert seed data
            insertUser("admin", "fd3bfce3-b6af-4fdd-940c-b67f9264d330", "dog");
            insertUser("prabhakar", "936a52ac-50d3-4e6d-aa2b-8e22538aec54", "cat");
            insertUser("june", "977baed1-cb88-4b83-882f-844386532b59", "sheep");
            insertUser("eve", "14b5ff42-a4d9-4d51-b526-56c54145f0c9", "bird");
            c.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    // Java program to calculate MD5 hash value
    public static String md5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertUser(String username, String password, String pet) {
       String sql = "INSERT INTO users (user_id, username, password, pet) VALUES (?, ?, ?, ?)";
       PreparedStatement pStatement = null;
       try {
          pStatement = connection().prepareStatement(sql);
          pStatement.setString(1, UUID.randomUUID().toString());
          pStatement.setString(2, username);
          pStatement.setString(3, md5(password));
          pStatement.setString(4, pet);
          pStatement.executeUpdate();
       } catch(Exception e) {
         e.printStackTrace();
       }
    }
}
