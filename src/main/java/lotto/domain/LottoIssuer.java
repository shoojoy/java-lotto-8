package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class LottoIssuer {
    public List<Lotto> issue(int count) {
        List<Lotto> tickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tickets.add(new Lotto(
                    Randoms.pickUniqueNumbersInRange(Lotto.MIN, Lotto.MAX, Lotto.COUNT)
            ));
        }
        return tickets;
    }
}
