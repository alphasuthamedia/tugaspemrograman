package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    long saldo;
    private static double TRANSACTION_FEE_PERCETAGE = 0.02;

    @Override
    public long processPayment(long amount) {
        return (long) (amount + (amount * TRANSACTION_FEE_PERCETAGE));
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
