package lotto.util;

import java.util.ArrayList;
import java.util.List;

public final class NumberParser {
    private NumberParser() {}

    public static List<Integer> parseCsvToIntegers(String csv) {
        if (csv == null) throw new IllegalArgumentException("[ERROR] 입력이 비어 있습니다.");
        String[] parts = csv.split(",");
        List<Integer> result = new ArrayList<>();
        for (String part : parts) {
            String s = part.trim();
            if (s.isEmpty()) throw new IllegalArgumentException("[ERROR] 잘못된 숫자 형식입니다.");
            result.add(parseIntStrict(s));
        }
        return result;
    }

    private static int parseIntStrict(String s) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("[ERROR] 숫자만 입력할 수 있습니다."); }
    }
}
