package lotto;

import lotto.domain.Lotto;
import lotto.domain.LottoIssuer;
import lotto.domain.LottoMatcher;
import lotto.domain.PurchaseAmount;
import lotto.domain.Rank;
import lotto.domain.Result;
import lotto.domain.WinningNumbers;
import lotto.util.NumberParser;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Application {

    public static void main(String[] args) {
        try {
            PurchaseAmount amount = readPurchaseAmountWithRetry();
            if (amount == null) return;

            List<Lotto> tickets = issueTickets(amount.count());
            OutputView.printIssued(tickets);

            WinningNumbers winning = readWinningNumbersFlow();
            if (winning == null) return;

            Result result = matchAllAndSummarize(tickets, winning);
            OutputView.printStats(result);
        } catch (NoSuchElementException ignore) {
        }
    }

    private static List<Lotto> issueTickets(int count) {
        return new LottoIssuer().issue(count);
    }

    static PurchaseAmount readPurchaseAmountWithRetry() {
        while (true) {
            String input;
            try {
                input = InputView.readPurchaseAmount();        // depth 1
            } catch (NoSuchElementException e) {
                return null;
            }

            try {
                long value = Long.parseLong(input.trim());     // depth 2
                return new PurchaseAmount(value);
            } catch (NumberFormatException e) {
                OutputView.printError("[ERROR] 숫자만 입력할 수 있습니다.");
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    static WinningNumbers readWinningNumbersFlow() {
        Lotto winning = readWinningLottoOnly();
        if (winning == null) return null;

        Integer bonus = readBonusNumberOnly(winning);
        if (bonus == null) return null;

        return new WinningNumbers(winning, bonus);
    }

    private static Lotto readWinningLottoOnly() {
        while (true) {
            String csv;
            try {
                csv = InputView.readWinningNumbers();          // depth 1
            } catch (NoSuchElementException e) {
                return null;
            }

            try {
                List<Integer> numbers = NumberParser.parseCsvToIntegers(csv); // depth 2
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private static Integer readBonusNumberOnly(Lotto winning) {
        while (true) {
            String raw;
            try {
                raw = InputView.readBonusNumber();
            } catch (NoSuchElementException e) {
                return null;
            }

            try {
                int bonus = Integer.parseInt(raw.trim());
                new WinningNumbers(winning, bonus);
                return bonus;
            } catch (NumberFormatException e) {
                OutputView.printError("[ERROR] 숫자만 입력할 수 있습니다.");
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private static Result matchAllAndSummarize(List<Lotto> tickets, WinningNumbers winning) {
        LottoMatcher matcher = new LottoMatcher();
        List<Rank> ranks = new ArrayList<>();

        for (Lotto t : tickets) {
            ranks.add(matcher.match(t, winning));
        }
        return Result.of(ranks, tickets.size());
    }
}
