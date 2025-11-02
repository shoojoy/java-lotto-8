// src/main/java/lotto/Application.java
package lotto;

import java.util.NoSuchElementException;
import lotto.domain.*;
import lotto.util.NumberParser;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        PurchaseAmount amount = readPurchaseAmountWithRetry();
        int count = amount.count();

        List<Lotto> tickets = new LottoIssuer().issue(count);
        OutputView.printIssued(tickets);

        WinningNumbers winning = readWinningWithRetry();

        LottoMatcher matcher = new LottoMatcher();
        List<Rank> ranks = new ArrayList<>();
        for (Lotto t : tickets) {
            ranks.add(matcher.match(t, winning));
        }
        Result result = Result.of(ranks, tickets.size());
        OutputView.printStats(result);
    }

    static PurchaseAmount readPurchaseAmountWithRetry() {
        while (true) {
            try {
                String input = InputView.readPurchaseAmount();
                long value = Long.parseLong(input.trim());
                return new PurchaseAmount(value);
            } catch (NoSuchElementException e) {
                System.exit(0);
                return null;
            } catch (NumberFormatException e) {
                OutputView.printError("[ERROR] 숫자만 입력할 수 있습니다.");
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    static WinningNumbers readWinningWithRetry() {
        while (true) {
            String csv;
            try {
                csv = InputView.readWinningNumbers();
            } catch (NoSuchElementException e) {
                System.exit(0);
                return null;
            }
            try {
                List<Integer> numbers = NumberParser.parseCsvToIntegers(csv);
                Lotto winning = new Lotto(numbers);
                // 보너스 번호 루프
                while (true) {
                    String bonusStr;
                    try {
                        bonusStr = InputView.readBonusNumber();
                    } catch (NoSuchElementException e) {
                        System.exit(0);
                        return null;
                    }
                    try {
                        int bonus = Integer.parseInt(bonusStr.trim());
                        return new WinningNumbers(winning, bonus);
                    } catch (NumberFormatException e) {
                        OutputView.printError("[ERROR] 숫자만 입력할 수 있습니다.");
                    } catch (IllegalArgumentException e) {
                        OutputView.printError(e.getMessage());
                    }
                }
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
