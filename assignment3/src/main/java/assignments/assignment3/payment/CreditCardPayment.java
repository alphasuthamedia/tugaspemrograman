package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    private static double TRANSACTION_FEE_PERCETAGE = 0.02;

    public long processPayment(long amount) {
        return (long) (amount + (amount * TRANSACTION_FEE_PERCETAGE));
    }
    
    //TODO: implementasikan class yang implement interface di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
}
