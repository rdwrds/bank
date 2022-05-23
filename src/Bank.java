import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    //all connections in try(); will be automatically closed after completed
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
            temp.displayAccount();
            String SQL = "INSERT INTO" + ACC_TABLE + "VALUES("
                    + String.valueOf(temp.getID()) + ", "
                    + temp.getFirstName()
                    + ", " + temp.getLastName()
                    + ", " + temp.getPassword()
                    + ", " + "0.0);";

            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();)
            {
                stmt.executeUpdate(SQL);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }


            customers.add(temp);
        }
        else
        {
            Account temp = new Account(firstName, lastName, bal);
            System.out.printf("nam = " + temp.getFirstName());
            String SQL = "INSERT INTO " + ACC_TABLE + " VALUES("
                    + String.valueOf(temp.getID())
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
                    int id;
                    String f;
                    String l;
                    String p;
                    float b;
                    while(rs.next()) //only executes once, since all IDs are unique
                    {
                        id = rs.getInt(1);
                        f = rs.getString(2);
                        l = rs.getString(3);
                        p = rs.getString(4);
                        b = rs.getFloat(5);
                        this.currentUser = new Account(id, f, l, p, b);
                        loggedIn = true;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }

}
