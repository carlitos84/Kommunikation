package Controller;

import BO.BOManager;

/**
 * Created by Teddy on 2016-10-03.
 */
public class User {
    private String username;
    private String password;
    private boolean verifedUser;
    public User()
    {
        this.username = null;
        this.password = null;
        this.verifedUser = false;
    }

    public boolean validateUser(String username, String password)
    {

        verifedUser =  BOManager.customerLogin(username, password);
        if(verifedUser)
        {
            setUsername(username);
            setPassword(password);
        }
        return verifedUser;
    }

    public boolean loginSuccess()
    {
        return verifedUser;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String newusername)
    {
        username = newusername;
    }

    public void setPassword(String newpassword)
    {
        password = newpassword;
    }
}
