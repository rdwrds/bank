import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Bank daBank = new Bank("Da Bank", false);

        int accessOption;
        int accountOption;

        daBank.test();

        do
        {
            System.out.println("Welcome to " + daBank.name + "!");
            System.out.println("Select 1 to log into Online Banking\n" 
                             + "Select 2 to Create an account\n" 
                             + "Select 3 to exit program.\n");
            accessOption = s.nextInt();
            switch(accessOption)
            {
                case 1:
                    daBank.logIn();
                    break;
                case 2:
                    daBank.createAccount();
                    for(Account a : Account.accounts) a.displayAccount();
                    break;
                case 3:
                     System.out.println("Thanks for banking with us, here at " + daBank.name + "!");
                     break;
                default:
                    System.out.println("You inserted a " + accessOption + "Please select one of the three options above\n");
                    break;
            }
            //if we are logged in, we dont need to validate current user, as they are both set at the same time in Bank.logIn()
            //we should still probably validate it, though.
            if(daBank.loggedIn)
            {
                Account user = daBank.currentUser;
                do
                {
                    System.out.println("Hello " + user.getFirstName() + "! Would you like to:\n"
                        + "Select 1 to View Account Details\n" 
                        + "Select 2 to Deposit\n" 
                        + "Select 3 to Withdraw\n"
                        + "Select 4 to create Credit Card\n"
                        + "Select 5 to Logout\n");
                    accountOption = s.nextInt();

                    switch(accountOption)
                    {
                        case 1:
                            user.displayAccount();
                            System.out.println("Your owned cards are:");
                            System.out.println(user.getCards());
                            break;
                        case 2:
                            float temp;
                            System.out.println("Enter how much money you'd like to deposit\nFormatted as 0.00");
                            temp = s.nextFloat();
                            user.deposit(temp);
                            break;
                        case 3:
                            float temp2;
                            System.out.println("Enter how much money you'd like to withdraw\nFormatted as 0.00");
                            temp2 = s.nextFloat();
                            user.withdraw(temp2);
                            break;
                        case 4:
                            user.createCard();
                            break;
                        case 5:
                            System.out.println("You have been logged out.");
                            daBank.currentUser = null;
                            daBank.loggedIn = false;
                            break;
                        default:
                            System.out.println("You inserted a " + accessOption + "Please select one of the three options above\n");
                            break;
                    }
                }while(accountOption != 5);
            }
        }while(accessOption != 3);
        System.out.println("Program exited.\n");

        //closes all other scanners, 
        s.close();
    }
}