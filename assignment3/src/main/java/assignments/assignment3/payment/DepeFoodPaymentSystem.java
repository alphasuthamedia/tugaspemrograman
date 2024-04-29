package assignments.assignment3.payment;

public interface DepeFoodPaymentSystem {
    long saldo = 0;

    public abstract long processPayment(long amount);

    public abstract void setSaldo(long saldo);
    
    public abstract long getSaldo(long saldo);
}
