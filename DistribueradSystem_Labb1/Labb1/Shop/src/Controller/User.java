package Controller;

import BO.BOManager;

/**
 * Created by Teddy on 2016-10-03.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private boolean verifedUser;
    private boolean customer;
    private boolean staff;
    private boolean admin;

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User()
    {
        this.id = -1;
        this.username = null;
        this.password = null;
        this.verifedUser = false;
        this.customer = false;
        this.staff = false;
        this.admin = false;
    }

    public boolean validateUser(String username, String password, String usertype)
    {
        verifedUser =  BOManager.userLogin(username, password, usertype);
        if(verifedUser)
        {
            setUsername(username);
            setPassword(password);
            switch (usertype)
            {
                case "customer":
                    setCustomer(true);
                    break;
                case "staff":
                    setStaff(true);
                    break;
                case "admin":
                    setAdmin(true);
                    break;
                default:
                    System.out.println("int BOManager-userLogin deafault!!!");
            }
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

    public String getPassword() {
        return password;
    }
}
