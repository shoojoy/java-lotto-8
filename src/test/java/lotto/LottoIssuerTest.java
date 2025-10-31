package lotto;

import lotto.domain.Lotto;
import lotto.domain.LottoIssuer;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class LottoIssuerTest {

    @Test
    void N장을_발행한다() {
        LottoIssuer issuer = new LottoIssuer();
        List<Lotto> tickets = issuer.issue(8);
        assertThat(tickets).hasSize(8);
    }

    @Test
    void 각_티켓은_6개_중복_없고_범위내_번호다() {
        LottoIssuer issuer = new LottoIssuer();
        Lotto ticket = issuer.issue(1).get(0);
        List<Integer> nums = ticket.numbers();

        assertThat(nums).hasSize(6);
        assertThat(new HashSet<>(nums)).hasSize(6);
        assertThat(nums).allMatch(n -> n >= 1 && n <= 45);
        assertThat(nums).isSorted();
    }
}
