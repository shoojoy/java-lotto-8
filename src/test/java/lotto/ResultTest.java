package lotto;

import lotto.domain.PurchaseAmount;
import lotto.domain.Rank;
import lotto.domain.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ResultTest {

    @Test
    @DisplayName("등수별 개수 집계 및 총 상금/총 비용 계산")
    void aggregate_counts_and_totals() {
        List<Rank> ranks = List.of(
                Rank.FIFTH, Rank.MISS, Rank.MISS, Rank.MISS,
                Rank.MISS, Rank.MISS, Rank.MISS, Rank.MISS
        );
        Result r = Result.of(ranks, 8);

        assertThat(r.countOf(Rank.FIFTH)).isEqualTo(1);
        assertThat(r.totalCost()).isEqualTo(8L * PurchaseAmount.PRICE_PER_TICKET);
        assertThat(r.totalPrize()).isEqualTo(Rank.FIFTH.prize());
    }

    @Test
    @DisplayName("수익률 포맷: 소수점 한 자리와 % 기호, 그룹 구분 기호 유지")
    void yield_formatting_one_decimal_percent() {
        // 5,000 / 8,000 = 62.5%
        List<Rank> ranks = List.of(
                Rank.FIFTH, Rank.MISS, Rank.MISS, Rank.MISS,
                Rank.MISS, Rank.MISS, Rank.MISS, Rank.MISS
        );
        Result r = Result.of(ranks, 8);
        assertThat(r.yieldPercentageRounded()).isEqualTo("62.5%");
    }

    @Test
    @DisplayName("티켓 0장일 때 0.0% 반환")
    void zero_ticket_returns_zero_percent() {
        Result r = Result.of(List.of(), 0);
        assertThat(r.yieldPercentageRounded()).isEqualTo("0.0%");
    }
}
