package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    long saldo;
    private static final double MINIMUM_TOTAL_PRICE = 50000;

    @Override
    public long processPayment(long amount) {
        return ((amount >= MINIMUM_TOTAL_PRICE) ? 1 : 0);
    }
    
    @Override
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    @Override
    public long getSaldo(long saldo) {
        return saldo;
    }
}
