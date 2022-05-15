public class CreditCard extends Card {
    //not money balance - limit left from previous cycle
    private float balance;
    private float limit;
    public double interest;

    public CreditCard(float bal, float lim, double inte)
    {
        //super() generate card object
        super();
        //manually setting credit card object
        this.balance = bal;
        this.limit = lim;
        this.interest = inte;
    }

    public float getBalance()
    {
        return this.balance;
    }

    public void setBalance(float bal)
    {
        //this would be a ternary statement but it would be nested, which is ugly.
        if(bal == 0.0f)
        {
            System.out.println("Attempted balance change of $0.00, balance unchanged.");
        }
        else
        {
            this.balance += (bal > 0.0f) ? bal : -(bal);
        }
    }

    public float getLimit()
    {
        return this.limit;
    }

    public void setLimit(float lim)
    {
        if(lim < 0.0f)
        {
            System.out.println("Limit cannot be negative.");
        }
        else
        {
            this.limit = lim;
        }
    }

    public double getInterest()
    {
        return this.interest;
    }

    public void setInterest(double inte)
    {
        if(interest < 0.0d)
        {
            System.out.println("Interest cannot be negative.");
        }
        else
        {
            this.interest = inte;
        }
    }



}
