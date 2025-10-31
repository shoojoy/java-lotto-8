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
        // TODO: 프로그램 구현
        // 1) 구매 금액 입력/검증 -> 장수 계산
        PurchaseAmount amount = readPurchaseAmountWithRetry();
        int count = amount.count();

        // 2) 장수만큼 발행 -> 발행 결과 출력
        List<Lotto> tickets = new LottoIssuer().issue(count);
        OutputView.printIssued(tickets);

        // 3) 당첨 번호 + 보너스 입력/검증
        WinningNumbers winning = readWinningWithRetry();

        // 4) 모든 티켓 매칭 -> 집계 -> 통계/수익률 출력
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
                OutputView.printError(e.getMessage());
                throw e;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    static WinningNumbers readWinningWithRetry() {
        while (true) {
            try {
                String csv = InputView.readWinningNumbers();
                List<Integer> numbers = NumberParser.parseCsvToIntegers(csv);
                Lotto winning = new Lotto(numbers);
                while (true) {
                    try {
                        String bonusStr = InputView.readBonusNumber();
                        int bonus = Integer.parseInt(bonusStr.trim());
                        return new WinningNumbers(winning, bonus);
                    } catch (NoSuchElementException e) {
                        OutputView.printError(e.getMessage());
                        throw e;
                    } catch (IllegalArgumentException e) {
                        OutputView.printError(e.getMessage());
                    }
                }
            } catch (NoSuchElementException e) {
                OutputView.printError(e.getMessage());
                throw e;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
