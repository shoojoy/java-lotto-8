package lotto.domain;

import java.util.ArrayList;
import java.util.List;

public class LottoIssuer {
    public List<Lotto> issue(int count) {
        List<Lotto> tickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tickets.add(Lotto.random());
        }
        return tickets;
    }
}
