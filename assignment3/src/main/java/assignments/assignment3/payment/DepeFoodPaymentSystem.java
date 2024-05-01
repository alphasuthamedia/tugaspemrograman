package assignments.assignment3.payment;

public interface DepeFoodPaymentSystem {

    public static final long saldo = 0;
    public abstract long processPayment(long amount);

    /* buat method abstrak yang kemungkinan akan digunakan di Debitpayment dan CreditCardPayment
     * dengan ini, kita bisa mengoverridenya di class tersebut
     */
    public abstract void setSaldo(long saldo);
    public abstract long getSaldo();
}
