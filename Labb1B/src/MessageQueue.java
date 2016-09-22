import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Teddy on 2016-09-22.
 */
public class MessageQueue{
    private int size;
    private ArrayList<String> messageList;

    public MessageQueue()
    {
        size = 0;
        messageList = new ArrayList<>();
    }

    public boolean add(String message)
    {
        size++;
        messageList.add(message);
        System.out.println("messageQueue add: " + message + " size: " + size);
        return true;
    }

    public String poll()
    {
        size--;
        return messageList.remove(0);
    }

    public boolean isEmpty()
    {
        if(size > 0)
        {
            return true;
        }
        return false;
    }


}
