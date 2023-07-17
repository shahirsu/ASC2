package com.amz.jcservice;

import org.springframework.boot.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import java.io.Serializable;
import com.amz.jcservice.Constants;
import java.util.Arrays;

@RestController
@EnableAutoConfiguration
public class LoginController {
  @Value("${app.secret}")
  private String secret;

  @CrossOrigin(origins = "*")
  @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
  LoginResponse login(@RequestBody LoginRequest input) {
    String username = input.username;
    //Check if username is in the allowlist, if not found set a static string for username
    if (!Arrays.asList(Constants.userList).contains(username)) {
      username = "not_found";
    }
    else {
    //if username is in the allowlist
      username = input.username;
    }
    User user = User.fetch(username);
    //Match the password hash for the password provided and in DB
    if (Postgres.md5(input.password).equals(user.hashedPassword)) {
      return new LoginResponse(user.token(secret));
    } else {
      //If passwords dont match return Unauthorized
      throw new Unauthorized("Access Denied");
    }
  }
}

class LoginRequest implements Serializable {
  public String username;
  public String password;
}

class LoginResponse implements Serializable {
  public String token;
  public LoginResponse(String msg) { this.token = msg; }
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class Unauthorized extends RuntimeException {
  public Unauthorized(String exception) {
    super(exception);
  }
}
