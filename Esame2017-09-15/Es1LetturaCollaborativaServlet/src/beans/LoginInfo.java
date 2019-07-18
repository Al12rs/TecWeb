package beans;

import java.io.Serializable;

public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String username;
    //bella così, in chiaro.
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    

}