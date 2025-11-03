package lotto.domain;

public class WinningNumbers {
    private final Lotto winning;
    private final int bonus;

    public WinningNumbers(Lotto winning, int bonus) {
        validate(winning, bonus);
        this.winning = winning;
        this.bonus = bonus;
    }

    private void validate(Lotto winning, int bonus) {
        if (!Lotto.isValidNumber(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (winning.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public Lotto winning() {
        return winning;
    }

    public int bonus() {
        return bonus;
    }
}
