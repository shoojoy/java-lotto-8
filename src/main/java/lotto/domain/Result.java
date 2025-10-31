package lotto.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Result {
    private final Map<Rank, Integer> counts = new EnumMap<>(Rank.class);
    private final int ticketCount;

    private Result(int ticketCount) {
        this.ticketCount = ticketCount;
        for (Rank r : Rank.values()) counts.put(r, 0);
    }

    public static Result of(List<Rank> ranks, int ticketCount) {
        Result r = new Result(ticketCount);
        for (Rank rank : ranks) {
            r.counts.put(rank, r.counts.get(rank) + 1);
        }
        return r;
    }

    public int countOf(Rank rank) {
        return counts.get(rank);
    }

    public long totalPrize() {
        long sum = 0;
        for (Rank r : Rank.values()) {
            if (r == Rank.MISS) continue; // MISS는 상금 0, 출력 제외 기준에도 부합
            sum += (long) counts.get(r) * r.prize();
        }
        return sum;
    }

    public long totalCost() {
        return (long) ticketCount * PurchaseAmount.PRICE_PER_TICKET;
    }

    public String yieldPercentageRounded() {
        long cost = totalCost();
        if (cost == 0) return "0.0%";
        BigDecimal raw = BigDecimal.valueOf(totalPrize())
                .divide(BigDecimal.valueOf(cost), 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        BigDecimal roundedOneDecimal = raw.setScale(1, RoundingMode.HALF_UP);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREA);
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);
        return nf.format(roundedOneDecimal) + "%";
    }
}
