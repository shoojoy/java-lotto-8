package lotto;

import lotto.util.NumberParser;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class NumberParserTest {
    @Test
    void csv를_정수_리스트로_파싱() {
        List<Integer> nums = NumberParser.parseCsvToIntegers("1, 2,3, 4,5,6");
        assertThat(nums).containsExactly(1,2,3,4,5,6);
    }

    @Test
    void 빈토큰이면_예외() {
        assertThatThrownBy(() -> NumberParser.parseCsvToIntegers("1,2,,4,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    void 숫자아니면_예외() {
        assertThatThrownBy(() -> NumberParser.parseCsvToIntegers("1,2,a,4,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
