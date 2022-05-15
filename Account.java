import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Account {
    private String fName;
    private String lName;
    private String password;
    private float balance;
    //ID will not have a setter - as it will not change
    private final int ID;

    //cards are set in constructor
    public ArrayList<Card> cards = new ArrayList<Card>();

    //variable that keep track of total accounts across ALL objects
    public static ArrayList<Account> accounts = new ArrayList<Account>();

    public static int getNumAccounts()
    {
        return accounts.size();
    }

    //constructor for if starting balance is 0
    public Account(String fN, String lN)
    {
        this.fName = fN;
        this.lName = lN;
        this.ID = generateID();
        this.password = setPassword();
        this.balance = 0.0f;
        accounts.add(this);
    }

    public Account(String fN, String lN, float bal)
    {
        this.fName = fN;
        this.lName = lN;
        this.ID = generateID();
        this.password = setPassword();
        this.balance = bal;
        accounts.add(this);
    }

    public Account(int i)
    {
        this.ID = i;
    }

    public String getFirstName()
    {
        return fName;
    }

    public void setFirstName(String n)
    {
        this.fName = n;
    }

    public String getLastName()
    {
        return lName;
    }

    public void setLastName(String n)
    {
        this.lName = n;
    }

    private String setPassword()
    {
        String copyOne = " ";
        String copyTwo = "";
        Scanner s = new Scanner(System.in);
        
        while(!copyOne.equals(copyTwo))
        {
            System.out.println("Enter Password:\n");
            copyOne = s.next(); //password is terminated after any white space
            System.out.println("Re-enter Password\n");
            copyTwo = s.next();
        }
        s.close();

        return copyOne;
    }

     String getPassword()
    {
        return this.password;
    }

    public float getBalance()
    {
        return balance;
    }

    public void withdraw(float amount)
    {
        if(amount > this.balance)
        {
            System.out.println("Insufficient Funds.\n");
        }
        else
        {
            this.balance -= amount;
        }
    }

    public void deposit(float amount)
    {
        if(amount == 0)
        {
            System.out.println("Cannot deposit nothing.");
        }
        else
        {
            this.balance += amount;
        }
    }

    private int generateID()
    {
        boolean found = false;
        Random r = new Random();
        int temp = r.nextInt(10000000);
        if(accounts.size() == 0)
        {
            return temp;
        }
        else
        {
            //while our ID has not been found
            while(!found) 
            {
                temp = r.nextInt(100000000);
                for(Account a : accounts)
                {
                    if(a.ID == temp)
                    {
                        break;
                    }
                }
                found = true;
            }
            return temp;
        }
    }

    public int getID()
    {
        return ID;
    }

    public String getCards()
    {
        String temp = "";
        for(Card c : cards)
        {
            temp += (c instanceof DebitCard) ? "Debit Card\n" : "Credit Card\n";
            temp += ("Card Number: ") + c.getCardNumber() + "\n";
            temp += ("Expiration: ") + c.getExpiration() + "\n";
            temp += ("CVV: ") + c.getCVV() + "\n";
        }

        return temp;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == this) return true;

        if(!(o instanceof Account)) return false;

        Account temp = (Account) o;

        return temp.getID() == this.ID;
    }
    

}
