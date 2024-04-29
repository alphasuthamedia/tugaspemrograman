package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    long saldo;
    private static final double MINIMUM_TOTAL_PRICE = 50000;

    @Override
    public long processPayment(long amount) {
        if (checkerMinimumTotalPriceTerpenuhi(amount)) {
            if (checkerSaldoMemenuhi(amount)) {
                return amount; // return amoung jika semua kondisi terpenuhi
            } else {
                System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
                return 0; // return 0 kita set untuk memberitahu bahwa proses pembayaran berhasil
            }
        } else {
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain");
            return 0; // return 0 kita set untuk memberitahu bahwa proses pembayaran gagal
        }
    }
    
    @Override
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    @Override
    public long getSaldo() {
        return saldo;
    }

    public boolean checkerMinimumTotalPriceTerpenuhi(long ammount){
        return ((ammount >= MINIMUM_TOTAL_PRICE) ? true : false);
    }
    
    public boolean checkerSaldoMemenuhi(long ammount){
        return saldo >= ammount;
    }
}
