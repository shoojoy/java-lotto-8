package lotto;

import lotto.domain.Rank;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @ParameterizedTest
    @CsvSource({
            "6,false,FIRST",
            "5,true,SECOND",
            "5,false,THIRD",
            "4,false,FOURTH",
            "3,false,FIFTH",
            "2,false,MISS",
            "0,false,MISS"
    })
    void 등수_판정(int match, boolean bonus, Rank expected) {
        assertThat(Rank.of(match, bonus)).isEqualTo(expected);
    }
}
