package Controllers.Backend.NetworkObjects;

import Controllers.Backend.NetworkObjects.LoginToken;

import java.io.Serializable;

public class ResetPasswordRequest implements Serializable {
    private LoginToken token;
    private String newHashedPassword;

    public ResetPasswordRequest(LoginToken token, String newHashedPassword) {
        this.token = token;
        this.newHashedPassword = newHashedPassword;
    }

    public LoginToken GetToken() {
        return token;
    }

    public String NewHashedPassword() {
        return newHashedPassword;
    }
}
