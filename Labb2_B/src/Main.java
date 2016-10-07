import java.io.IOException;

/**
 * Created by Teddy on 2016-09-27.
 */
public class Main {

    public static void main(String args[])
    {
        int port = 4445;
            if(args.length == 1)
            {
                port = Integer.parseInt(args[0]);
            }

            Thread t = new Thread(new ClientListener(port));
        t.run();
    }

}
