package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    public static final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    public long countTransactionFee(long amount) {
        return (long) (amount * TRANSACTION_FEE_PERCENTAGE);
    }

    // @Override
    // public class DebitPayment {
    //     private double balance;

    //     public DebitPayment(double balance) {
    //         this.balance = balance;
    //     }

    //     public double getBalance() {
    //         return balance;
    //     }

    //     public void setBalance(double balance) {
    //         this.balance = balance;
    //     }

    //     public double processPayment(double amount) {
    //         double transactionFee = amount * TRANSACTION_FEE_PERCENTAGE;
    //         double totalAmount = amount + transactionFee;
    //         if (totalAmount > balance) {
    //             return -1;
    //         }
    //         balance -= totalAmount;
    //         return balance;
    //     }
    // }
    //TODO implementasikan class di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
}
