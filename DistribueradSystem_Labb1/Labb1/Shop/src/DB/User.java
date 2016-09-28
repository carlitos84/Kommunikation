package DB;

/**
 * Created by Teddy on 2016-09-28.
 */
public class User {
    private int Id;
    private String username;
    private String password;

    public User(int Id, String username, String password)
    {
        this.Id = Id;
        this.username = username;
        this.password = password;
    }

    public String toString()
    {
        return String.valueOf(Id) +" " + username + " " + password;
    }
}
