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
        try {
            PurchaseAmount amount = readPurchaseAmountWithRetry();
            if (amount == null) return;
            int count = amount.count();

            List<Lotto> tickets = new LottoIssuer().issue(count);
            OutputView.printIssued(tickets);

            WinningNumbers winning = readWinningWithRetry();
            if (winning == null) return;

            LottoMatcher matcher = new LottoMatcher();
            List<Rank> ranks = new ArrayList<>();
            for (Lotto t : tickets) {
                ranks.add(matcher.match(t, winning));
            }
            Result result = Result.of(ranks, tickets.size());
            OutputView.printStats(result);
        } catch (java.util.NoSuchElementException ignore) {
        }
    }

    static PurchaseAmount readPurchaseAmountWithRetry() {
        while (true) {
            String input;
            try {
                input = InputView.readPurchaseAmount();
            } catch (java.util.NoSuchElementException e) {
                return null;
            }
            try {
                long value = Long.parseLong(input.trim());
                return new PurchaseAmount(value);
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
            } catch (java.util.NoSuchElementException e) {
                return null;
            }
            try {
                List<Integer> numbers = lotto.util.NumberParser.parseCsvToIntegers(csv);
                Lotto winning = new Lotto(numbers);

                while (true) {
                    String bonusStr;
                    try {
                        bonusStr = InputView.readBonusNumber();
                    } catch (java.util.NoSuchElementException e) {
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
