package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    long saldo;
    private static final double TRANSACTION_FEE_PERCETAGE = 0.02;

    /* Override processPayment(long amount) yang ada di DepeFoodPaymentSystem */
    @Override
    public long processPayment(long amount) {
        if (checkerSaldoMemenuhi(amount)) {
            // Jika saldo tercukupi langsung return total harganya yang telah ditambah dengan tax
            return (long) (amount + (amount * TRANSACTION_FEE_PERCETAGE));
        } else {
            // jika saldo tidak mencukupi, maka return 0 dan jangan lupa print pesan saldo tidak cukup
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return 0;
        }
    }
    
    /* Override processPayment(long amount) yang ada di DepeFoodPaymentSystem */
    @Override
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    /* Override processPayment(long amount) yang ada di DepeFoodPaymentSystem */
    @Override
    public long getSaldo() {
        return saldo;
    }

    /* cek apakah saldo dari user cukup untuk membayar total harga makannnya */
    public boolean checkerSaldoMemenuhi(long ammount){
        return saldo >= ammount;
    }
}
