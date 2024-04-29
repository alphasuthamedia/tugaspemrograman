package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    private static final double MINIMUM_TOTAL_PRICE = 50000;

    public long processPayment(long amount) {
        return ((amount >= MINIMUM_TOTAL_PRICE) ? 1 : 0);
    }
    //TODO implementasikan class di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
}
