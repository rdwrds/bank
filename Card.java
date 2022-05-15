import java.util.Random;

import java.util.ArrayList;
public class Card {
    private String cardNumber;
    private String expiration;
    private int CVV;

    private static ArrayList<Card> totalCards = new ArrayList<Card>();

    public static int getTotalCards()
    {
        return totalCards.size();
    }
    
    public Card()
    {
        Random r = new Random();
        this.cardNumber = generateCardNumber();
        this.expiration = Integer.toString(r.nextInt(1,13)) + "/" + (Integer.toString(r.nextInt(22,100)));
        this.CVV = r.nextInt(100,1000);
        totalCards.add(this);
    }

    public String generateCardNumber()
    {
        boolean found = false;
        Random r = new Random();
        String subOne = Integer.toString(r.nextInt(10,100));
        String subTwo = Integer.toString(r.nextInt(1000,10000));
        String subThree = Integer.toString(r.nextInt(1000,10000));
        String subFour = Integer.toString(r.nextInt(1000,10000));
        String fullNum = "6" + subOne + "7" + "-" + subTwo + "-" + subThree + "-" + subFour;
        if(totalCards.size() == 0)
        {
            return fullNum;
        }
        else
        {
             //while this card hasn't been found
             while(!found) 
             {
                subOne = Integer.toString(r.nextInt(10,100));
                subTwo = Integer.toString(r.nextInt(1000,10000));
                subThree = Integer.toString(r.nextInt(1000,10000));
                subFour = Integer.toString(r.nextInt(1000,100000));
                fullNum = "6" + subOne + "7" + "-" + subTwo + "-" + subThree + "-" + subFour;
                for(Card c : totalCards)
                {
                    if(c.getCardNumber() == fullNum)
                    {
                       break;
                    }
                }
                found = true;
             }
             return fullNum;
        }
    }

    //wont show entire card number- this can be a number
    public String getCardNumber()
    {
       if(this.cardNumber.substring(15, 19) == "XXXX")
        {
            return "Card does not exist.";
        }
        else
        {
            return this.cardNumber;
        }
    }

    public String getExpiration()
    {
        return expiration;
    }

    public int getCVV()
    {
        return CVV;
    }

}

