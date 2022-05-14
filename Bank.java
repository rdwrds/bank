import java.util.ArrayList;
import java.util.Scanner;



public class Bank {
    public String name;
    public final boolean FDIC;
    public int currentUser;
    public boolean loggedIn;

    private ArrayList<Account> customers = new ArrayList<Account>();

    public Bank(String n, boolean fdic)
    {
        this.name = n;
        this.FDIC = fdic;
    }

    public void createAccount()
    {
        String firstName;
        String lastName;
        float bal;
        Scanner s = new Scanner(System.in);
        do
        {
            System.out.println("Please enter first name.");
            firstName = s.next();
        } while(firstName.equals(null) || firstName.equals(""));

        do
        {
            System.out.println("Please enter last name.");
            lastName = s.next();
        } while(lastName.equals(null) || lastName.equals(""));

        System.out.println("Please enter your starting balance.");
        bal = s.nextFloat();

        if(bal == 0.0f)
        {
            customers.add(new Account(firstName, lastName));
        }
        else
        {
            customers.add(new Account(firstName, lastName, bal));
        }

        s.close();
    }

    public void logIn()
    {
        int x;
        String pass;
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your ID");
        x = s.nextInt();

        System.out.println("Please enter your password.");
        pass = s.next();
        
        if(customers.size() == 0)
        {
            System.out.println("You must create an account!");
            createAccount();
        }

        for(Account a : customers)
        {
            if(a.getID() == x)
            {
                //this is not how password checking should go- 
                //but for our purposes it will work!
                if(pass == a.getPassword())
                {
                    currentUser = a.getID();
                }
            }
        }


        s.close();
    }

}
