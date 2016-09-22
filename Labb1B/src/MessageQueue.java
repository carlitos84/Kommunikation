import java.util.ArrayList;

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


    //Vi implementerade isEmpty() tvärtom här och även när vi kallade på den
    public boolean isEmpty()
    {
        if(size > 0)
        {
            return false;
        }
        return true;
    }


}
