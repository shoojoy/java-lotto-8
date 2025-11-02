package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.Result;

import java.util.List;

public class OutputView {
    public static void printIssued(List<Lotto> tickets) {
        System.out.println(tickets.size() + "개를 구매했습니다.");
        for (Lotto t : tickets) {
            System.out.println(t.numbers());
        }
        System.out.println();
    }

    public static void printStats(Result result) {
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.println("3개 일치 (5,000원) - " + result.countOf(Rank.FIFTH) + "개");
        System.out.println("4개 일치 (50,000원) - " + result.countOf(Rank.FOURTH) + "개");
        System.out.println("5개 일치 (1,500,000원) - " + result.countOf(Rank.THIRD) + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - " + result.countOf(Rank.SECOND) + "개");
        System.out.println("6개 일치 (2,000,000,000원) - " + result.countOf(Rank.FIRST) + "개");
        System.out.println("총 수익률은 " + result.yieldPercentageRounded() + "입니다.");
    }

    public static void printError(String message) {
        if (message != null && message.startsWith("[ERROR]")) {
            System.out.println(message);
            return;
        }
        System.out.println("[ERROR] " + message);
    }
}
