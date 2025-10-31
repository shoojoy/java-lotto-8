package lotto;

import lotto.domain.Lotto;
import lotto.domain.LottoMatcher;
import lotto.domain.Rank;
import lotto.domain.WinningNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoMatcherTest {

    @Test
    @DisplayName("6개 일치면 1등")
    void first_when6Matches() {
        Lotto t = new Lotto(List.of(1,2,3,4,5,6));
        WinningNumbers w = new WinningNumbers(new Lotto(List.of(1,2,3,4,5,6)), 7);
        assertThat(new LottoMatcher().match(t, w)).isEqualTo(Rank.FIRST);
    }

    @Test
    @DisplayName("5개+보너스면 2등")
    void second_when5PlusBonus() {
        Lotto t = new Lotto(List.of(1,2,3,4,5,7));
        WinningNumbers w = new WinningNumbers(new Lotto(List.of(1,2,3,4,5,6)), 7);
        assertThat(new LottoMatcher().match(t, w)).isEqualTo(Rank.SECOND);
    }

    @Test
    @DisplayName("5개만 일치면 3등")
    void third_when5Matches() {
        Lotto t = new Lotto(List.of(1,2,3,4,5,8));
        WinningNumbers w = new WinningNumbers(new Lotto(List.of(1,2,3,4,5,6)), 7);
        assertThat(new LottoMatcher().match(t, w)).isEqualTo(Rank.THIRD);
    }

    @Test
    @DisplayName("4개면 4등, 3개면 5등, 그 외 MISS")
    void fourth_fifth_miss() {
        Lotto t4 = new Lotto(List.of(1,2,3,4,10,11));
        Lotto t5 = new Lotto(List.of(1,2,3,40,41,42));
        Lotto t0 = new Lotto(List.of(7,8,9,10,11,12));
        WinningNumbers w = new WinningNumbers(new Lotto(List.of(1,2,3,4,5,6)), 7);

        LottoMatcher m = new LottoMatcher();
        assertThat(m.match(t4, w)).isEqualTo(Rank.FOURTH);
        assertThat(m.match(t5, w)).isEqualTo(Rank.FIFTH);
        assertThat(m.match(t0, w)).isEqualTo(Rank.MISS);
    }
}
