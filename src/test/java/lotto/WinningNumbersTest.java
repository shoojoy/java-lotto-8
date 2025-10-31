package lotto;

import lotto.domain.Lotto;
import lotto.domain.WinningNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WinningNumbersTest {

    @DisplayName("보너스 번호가 당첨 번호와 중복이면 예외")
    @Test
    void bonusDuplicate_shouldThrow() {
        Lotto w = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> new WinningNumbers(w, 6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("보너스 번호가 범위를 벗어나면 예외")
    @Test
    void bonusOutOfRange_shouldThrow() {
        Lotto w = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> new WinningNumbers(w, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
        assertThatThrownBy(() -> new WinningNumbers(w, 46))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
