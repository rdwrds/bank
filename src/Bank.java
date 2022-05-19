import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

    static final String DB_URL = "jdbc:mysql://localhost:3306/accounts";

    static final String USER = "root";

    static final String PASS = "SQLJavaShit90210";

    static final String ACC_TABLE =  "accs";

    public String name;
    public final boolean FDIC;
    public Account currentUser;
    public boolean loggedIn;
    Scanner s = new Scanner(System.in);

    private ArrayList<Account> customers = new ArrayList<Account>();

    //className: oracle.jdbc.driver.OracleDriver
    public void test()
    {

    }

    public Bank(String n, boolean fdic)
    {
        this.name = n;
        this.FDIC = fdic;
        //loads class driver for bank when initialized
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }


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
            Account temp = new Account(firstName, lastName);
            String SQL = "INSERT INTO" + ACC_TABLE + "VALUES("
                    + String.valueOf(temp.getID()) + ", "
                    + temp.getFirstName()
                    + ", " + temp.getLastName()
                    + ", " + temp.getPassword()
                    + ", " + "0.0);";

            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            } catch (SQLException e) {
                e.printStackTrace();
            }


            customers.add(temp);
        }
        else
        {
            Account temp = new Account(firstName, lastName, bal);
            String SQL = "INSERT INTO "
                    + ACC_TABLE
                    + " VALUES(" + String.valueOf(temp.getID())
                    + ", " + "\'" + temp.getFirstName() + "\'"
                    +  ", " + "\'" + temp.getLastName()  + "\'"
                    + ", " + "\'" + temp.getPassword() +  "\'"
                    + ", " + bal + ");";
            System.out.println(SQL);
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
                //puts acc into database
                stmt.executeUpdate(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            customers.add(temp);
        }

        //s.close();
    }

    public void logIn()
    {
        //boolean found = false;
            int x = 0;
            String pass;
            System.out.println("Please enter your ID");
            x = s.nextInt();

            System.out.println("Please enter your password.");
            pass = s.next();
            String SQL = "SELECT * FROM "
                    + ACC_TABLE
                    + " WHERE id=" + x
                    + " AND "
                    + "pass=" + "\'" + pass + "\';";
            System.out.println(SQL);
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();)
            {
                ResultSet rs = stmt.executeQuery(SQL);
                if(!rs.isBeforeFirst())
                {
                    System.out.println("Account not found.");
                }
                else
                {

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            /*
            do
            {
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
             */
    }

}
