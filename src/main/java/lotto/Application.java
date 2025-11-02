package lotto;

import lotto.domain.*;
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

            List<Lotto> tickets = new LottoIssuer().issue(amount.count());
            OutputView.printIssued(tickets);

            WinningNumbers winning = readWinningNumbersFlow();
            if (winning == null) return;

            Result result = matchAllAndSummarize(tickets, winning);
            OutputView.printStats(result);
        } catch (NoSuchElementException ignore) {
        }
    }

    static PurchaseAmount readPurchaseAmountWithRetry() {
        while (true) {
            String input = readLineOrNullForAmount();
            if (input == null) return null;

            PurchaseAmount amt = toPurchaseAmount(input);
            if (amt != null) return amt;
        }
    }

    private static String readLineOrNullForAmount() {
        try { return InputView.readPurchaseAmount(); }
        catch (NoSuchElementException e) { return null; }
    }

    private static PurchaseAmount toPurchaseAmount(String input) {
        try {
            long value = Long.parseLong(input.trim());
            return new PurchaseAmount(value);
        } catch (NumberFormatException e) {
            OutputView.printError("[ERROR] 숫자만 입력할 수 있습니다.");
            return null;
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return null;
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
            String csv = readLineOrNullForWinning();
            if (csv == null) return null;

            Lotto lotto = toWinningLotto(csv);
            if (lotto != null) return lotto;
        }
    }

    private static String readLineOrNullForWinning() {
        try { return InputView.readWinningNumbers(); }
        catch (NoSuchElementException e) { return null; }
    }

    private static Lotto toWinningLotto(String csv) {
        try {
            List<Integer> numbers = NumberParser.parseCsvToIntegers(csv);
            return new Lotto(numbers);
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return null;
        }
    }

    private static Integer readBonusNumberOnly(Lotto winning) {
        while (true) {
            String raw = readLineOrNullForBonus();
            if (raw == null) return null;

            Integer bonus = toBonusOrNull(winning, raw);
            if (bonus != null) return bonus;
        }
    }

    private static String readLineOrNullForBonus() {
        try { return InputView.readBonusNumber(); }
        catch (NoSuchElementException e) { return null; }
    }

    private static Integer toBonusOrNull(Lotto winning, String raw) {
        try {
            int bonus = Integer.parseInt(raw.trim());
            new WinningNumbers(winning, bonus); // 검증 목적
            return bonus;
        } catch (NumberFormatException e) {
            OutputView.printError("[ERROR] 숫자만 입력할 수 있습니다.");
            return null;
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return null;
        }
    }

    private static Result matchAllAndSummarize(List<Lotto> tickets, WinningNumbers winning) {
        LottoMatcher matcher = new LottoMatcher();
        List<Rank> ranks = new ArrayList<>();
        for (Lotto t : tickets) ranks.add(matcher.match(t, winning));
        return Result.of(ranks, tickets.size());
    }
}
