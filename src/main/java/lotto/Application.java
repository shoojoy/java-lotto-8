package lotto;

import lotto.domain.Lotto;
import lotto.domain.PurchaseAmount;
import lotto.domain.WinningNumbers;
import lotto.util.NumberParser;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        PurchaseAmount amount = readPurchaseAmountWithRetry();
        WinningNumbers winning = readWinningWithRetry();
    }
    static PurchaseAmount readPurchaseAmountWithRetry() {
        while (true) {
            try {
                String input = InputView.readPurchaseAmount();
                long value = Long.parseLong(input.trim());
                return new PurchaseAmount(value);
            } catch (Exception e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    static WinningNumbers readWinningWithRetry() {
        while (true) {
            try {
                String csv = InputView.readWinningNumbers();
                List<Integer> numbers = NumberParser.parseCsvToIntegers(csv);
                Lotto winning = new Lotto(numbers); // 개수/범위/중복 검증
                while (true) {
                    try {
                        String bonusStr = InputView.readBonusNumber();
                        int bonus = Integer.parseInt(bonusStr.trim());
                        return new WinningNumbers(winning, bonus); // 범위/중복 검증
                    } catch (Exception e) {
                        OutputView.printError(e.getMessage()); // 보너스 단계 재입력
                    }
                }
            } catch (Exception e) {
                OutputView.printError(e.getMessage()); // 당첨 번호 단계 재입력
            }
        }
    }
}
