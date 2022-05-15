public class Main {
    public static void main(String[] args) {
        Account jason = new Account("Billy", "Bob");
        
        DebitCard dCard = new DebitCard();

        System.out.println(dCard.getCVV());
        System.out.println(dCard.getCardNumber());

        jason.displayAccount();
    }
}