package BO;

import DB.DBCustomer;

/**
 * Created by Teddy on 2016-09-28.
 */
public class User {
    private int Id;
    private String username;
    private String password;
    private boolean customer;
    private boolean staff;
    private boolean admin;


    public User(int Id, String username, String password, boolean customer, boolean staff, boolean admin)
    {
        this.Id = Id;
        this.username = username;
        this.password = password;
        this.customer = customer;
        this.staff = staff;
        this.admin = admin;
    }

    public String toString()
    {
        return String.valueOf(Id) +" " + username + " " + password;
    }
    public boolean isCustomer()
    {
        return customer;
    }
    public boolean isStaff()
    {
        return staff;
    }
    public boolean isAdmin()
    {
        return admin;
    }
    public int getId()
    {
        return this.Id;
    }

    public static User getUser(String username, String password)
    {
        return DBCustomer.getCustomerByLogin(username, password);
    }
}
