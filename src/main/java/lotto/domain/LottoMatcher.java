package lotto.domain;

import java.util.HashSet;
import java.util.Set;

public class LottoMatcher {

    public Rank match(Lotto ticket, WinningNumbers winning) {
        int matchCount = intersectionCount(ticket, winning);
        boolean bonusMatched = ticket.contains(winning.bonus());
        return Rank.of(matchCount, bonusMatched);
    }

    private int intersectionCount(Lotto ticket, WinningNumbers winning) {
        Set<Integer> set = new HashSet<>(ticket.numbers());
        int count = 0;
        for (int n : winning.winning().numbers()) {
            if (set.contains(n)) count++;
        }
        return count;
    }
}
