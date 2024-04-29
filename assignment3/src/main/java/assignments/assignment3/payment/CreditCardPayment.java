package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    long saldo;
    private static double TRANSACTION_FEE_PERCETAGE = 0.02;

    @Override
    public long processPayment(long amount) {
        if (checkerSaldoMemenuhi(amount)) {
            // Jika saldo tercukupi langsung return total harganya yang telah ditambah dengan tax
            return (long) (amount + (amount * TRANSACTION_FEE_PERCETAGE));
        } else {
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return 0;
        }
    }
    
    @Override
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    @Override
    public long getSaldo(long saldo) {
        return saldo;
    }

    public boolean checkerSaldoMemenuhi(long ammount){
        return saldo >= ammount;
    }
}
