package lotto;

import lotto.domain.PurchaseAmount;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PurchaseAmountTest {

    @Test
    void 천원단위_양수_정상() {
        PurchaseAmount a = new PurchaseAmount(8000);
        assertThat(a.count()).isEqualTo(8);
    }

    @Test
    void 음수면_예외() {
        assertThatThrownBy(() -> new PurchaseAmount(-1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    void 천원단위_아니면_예외() {
        assertThatThrownBy(() -> new PurchaseAmount(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
