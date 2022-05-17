import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    public String name;
    public final boolean FDIC;
    public Account currentUser;
    public boolean loggedIn;
    Scanner s = new Scanner(System.in);
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

        //s.close();
    }

    public void logIn()
    {
        boolean found = false;
        do
        {
            int x = 0;
            String pass;
            System.out.println("Please enter your ID");
            x = s.nextInt();

            System.out.println("Please enter your password.");
            pass = s.next();

            for(Account a : Account.accounts)
            {
                if(a.getID() == x)
                {
                    //this is not how password checking should go- 
                    //but for our purposes it will work!
                    if(pass.equals(a.getPassword()))
                    {
                        found = true;
                        System.out.println("Log-in Successful! Welcome to " + this.name + "!");
                        this.currentUser = a;
                        this.loggedIn = true;
                    }
                }
                //if the account with our ID was not found
                if(!found) System.out.println("Your account does not exist, or your information was incorrect.\n");
        }
        } while(!found);
    }

}
