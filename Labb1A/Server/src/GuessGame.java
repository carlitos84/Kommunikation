import java.util.Random;

/**
 * Created by Teddy on 2016-09-22.
 */
public class GuessGame {
    private int correctNumber;

    public GuessGame()
    {
        Random rand = new Random();
        correctNumber = rand.nextInt(100) + 1;
        System.out.println("correct number: " + correctNumber);
    }

    public String makeGuess(int guessNumber)
    {
        if(guessNumber == correctNumber)
        {
            resetGame();
            return "CORRECT! A new game has started. Guess again!";
        }
        else if(guessNumber < correctNumber)
        {
            return "LO";
        }
        else
        {
            return "HI";
        }
    }

    private void resetGame()
    {
        Random rand = new Random();
        correctNumber = rand.nextInt(100) + 1;
        System.out.println("correct number: " + correctNumber);
    }
}
