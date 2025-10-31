package lotto.domain;

public class PurchaseAmount {
    public static final int PRICE_PER_TICKET = 1_000;

    private final long amount;

    public PurchaseAmount(long amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 구매 금액은 0보다 커야 합니다.");
        }
        if (amount % PRICE_PER_TICKET != 0) {
            throw new IllegalArgumentException("[ERROR] 구매 금액은 1,000원 단위여야 합니다.");
        }
    }

    public int count() {
        return (int) (amount / PRICE_PER_TICKET);
    }

    public long value() {
        return amount;
    }
}
