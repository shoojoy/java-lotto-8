package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private static final int MIN = 1;
    private static final int MAX = 45;
    private static final int COUNT = 6;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        List<Integer> copy = new ArrayList<>(numbers);
        Collections.sort(copy);
        this.numbers = Collections.unmodifiableList(copy);
    }
    // 여기 로또 안에서 부르면 자동으로 생성 되게끔

    public static Lotto random() {
        return new Lotto(
                Randoms.pickUniqueNumbersInRange(MIN, MAX, COUNT)
        );
    }

    public static boolean isValidNumber(int n) {
        return n >= MIN && n <= MAX;
    }

    private void validate(List<Integer> numbers) {
        if (numbers == null || numbers.size() != COUNT) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() != COUNT) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
        for (int n : numbers) {
            if (n < MIN || n > MAX) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
            }
        }
    }

    public List<Integer> numbers() {
        return numbers;
    }

    public boolean contains(int n) {
         return numbers.contains(n);
    }
}
