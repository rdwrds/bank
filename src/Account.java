import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;



public class Account {
    private String fName;
    private String lName;
    private String password;
    private float balance;
    //ID will not have a setter - as it will not change
    private final int ID;
    Scanner s = new Scanner(System.in);

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
    //constructor for creating temporoary account object that already exists.
    //does not add account to accounts list (already exists if ran)
    public Account(int id, String fN, String lN, String pass, float bal)
    {
        this.fName = fN;
        this.lName = lN;
        this.ID = id;
        this.password = pass;
        this.balance = bal;
    }


    public void displayAccount()
    {
        try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
            Statement stmt = conn.createStatement();)
        {
            String SQL = "SELECT id, fname, lname, balance FROM accs"
                        + " WHERE id=" + this.getID() + ";";
            ResultSet rs = stmt.executeQuery(SQL);
            if(!rs.isBeforeFirst())
            {
                System.out.println("Account does not exist in Database.");
            }
            else
            {
                while(rs.next())
                {
                    System.out.println("Account ID: " + rs.getInt(1));
                    System.out.println("First Name: " + rs.getString(2));
                    System.out.println("Last Name: " + rs.getString(3));
                    System.out.println("Balance: " + rs.getFloat(4));

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getFirstName()
    {
        String n = "";
        try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
            Statement stmt = conn.createStatement();)
        {
            String SQL = "SELECT fname FROM accs "
                    + "WHERE id=" + this.getID() + ";";
            ResultSet rs = stmt.executeQuery(SQL);
            if(!rs.isBeforeFirst())
            {
                System.out.println("Account does not exist in Database.");
                //b/c the object exists, we can grab its value instead of parsing DB
                n = this.fName;
            }
            else
            {
                rs.next();
                n = rs.getString(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return n;
    }

    public void setFirstName(String n)
    {
        this.fName = n;
    }

    public String getLastName()
    {
        String n = "";
        try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
            Statement stmt = conn.createStatement();)
        {
            String SQL = "SELECT lname FROM accs "
                    + "WHERE id=" + this.getID() + ";";
            ResultSet rs = stmt.executeQuery(SQL);
            if(!rs.isBeforeFirst())
            {
                System.out.println("Account does not exist in Database.");
                n = this.lName;
            }
            else
            {
                rs.next();
                n = rs.getString(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return n;
    }

    public void setLastName(String n)
    {
        this.lName = n;
    }

    private String setPassword()
    {
        String copyOne = " ";
        String copyTwo = "";
        
        while(!copyOne.equals(copyTwo))
        {
            System.out.println("Enter Password:\n");
            copyOne = s.next(); //password is terminated after any white space
            System.out.println("Re-enter Password\n");
            copyTwo = s.next();
        }


        return copyOne;
    }

     String getPassword()
    {
        String n = "";
        try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
            Statement stmt = conn.createStatement();)
        {
            String SQL = "SELECT pass FROM accs "
                    + "WHERE id=" + this.getID() + ";";
            ResultSet rs = stmt.executeQuery(SQL);
            if(!rs.isBeforeFirst())
            {
                System.out.println("Account does not exist in Database.");
                n = this.password;
            }
            else
            {
                rs.next();
                n = rs.getString(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return n;
    }

    public float getBalance()
    {
        float bal = 0.0f;
        try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
            Statement stmt = conn.createStatement();)
        {
            String SQL = "SELECT balance FROM accs "
                    + "WHERE id=" + this.getID() + ";";
            ResultSet rs = stmt.executeQuery(SQL);
            if(!rs.isBeforeFirst())
            {
                System.out.println("Account does not exist in Database.");
                bal = this.balance;
            }
            else
            {
                rs.next();
                bal = rs.getFloat(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bal;
    }

    public void withdraw(float amount)
    {
        if(amount > this.balance)
        {
            System.out.println("Insufficient Funds.\n");
        }
        else
        {
            float diff = this.balance - amount;
            String SQL = "UPDATE accs SET balance="
                        + diff
                        + " WHERE id=" + this.ID;
            try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
                Statement stmt = conn.createStatement();)
            {
                stmt.executeUpdate(SQL);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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
            float diff = this.balance + amount;
            String SQL = "UPDATE accs SET balance="
                    + diff
                    + " WHERE id=" + this.ID;
            try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
                Statement stmt = conn.createStatement();)
            {
                stmt.executeUpdate(SQL);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private int generateID()
    {
        boolean found = false;
        Random r = new Random();
        int temp = r.nextInt(100000);
        if(accounts.size() == 0)
        {
            return temp;
        }
        else
        {
            //while our ID has not been found
            while(!found) 
            {
                temp = r.nextInt(1000000);
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
        //since only the ID is unique, any SQL query would be cyclic
        //or might grab more than 1 account based on other fields
        return this.ID;
    }

    public void createCard()
    {
        int temp;
        System.out.println("Enter 1 for a Credit Card\nEnter 2 For Debit Card");
        temp = s.nextInt();
        Card c = (temp == 1) ? new CreditCard(0.0f, 500.0f, 2.0) : new DebitCard();
        cards.add(c);
        System.out.println(c.getCardNumber() + " " + c.getExpiration() + " " + c.getCVV());

        try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER,Bank.PASS);
            Statement stmt = conn.createStatement();)
        {
            ++Card.cardID;
            String SQL="INSERT INTO cards VALUES("
                    + Card.cardID + ","
                    + this.ID + ","
                    + "\'Credit Card\',"
                    + "\'" + c.getCardNumber()
                    +"\'," + "\'9/72\',"
                    + c.getCVV() + ");";
            stmt.executeUpdate(SQL);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getCards()
    {
        String n = "";
        try(Connection conn = DriverManager.getConnection(Bank.DB_URL, Bank.USER, Bank.PASS);
            Statement stmt = conn.createStatement();)
        {
            String SQL = "SELECT cardNum, expiration, CVV, cardType FROM cards "
                    + "WHERE id=" + this.getID() + ";";
            ResultSet rs = stmt.executeQuery(SQL);
            if(!rs.isBeforeFirst())
            {
                System.out.println("Account does not exist in Database.");
            }
            else
            {
                while(rs.next())
                {
                    n += "\nCard Type: " + rs.getString(4);
                    n += "\nCard Number: " + rs.getString(1);
                    n += "\nExpiration: " + rs.getString(2);
                    n += "\nCVV: " + rs.getInt(3) + "\n";
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return n;
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
