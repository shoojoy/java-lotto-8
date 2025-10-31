# java-lotto-precourse

간단한 로또 발매기 콘솔 앱. 1~45 범위의 중복 없는 6개 번호로 티켓을 발행하고<br>
당첨 번호(6개) + 보너스(1개) 와 비교해 당첨 통계 및 총 수익률을 출력합니다.

## 실행 환경
 - Java 21
 - Gradle Wrapper (제공 build.gradle 변경 금지)
 - 라이브러리: camp.nextstep.edu.missionutils의 Randoms, Console

## 실행 방법
 - ./gradlew clean test
 - ./gradlew run

## 기능 요구사항
 - 로또 발행: 1~45, 중복 없음 6개.
 - 당첨 번호: 6개 + 보너스 1개.
 - 구매 금액(1,000원 단위)만큼 발행 -> 번호는 오름차순 출력.
 - 당첨 통계 및 총 수익률(소수점 둘째 자리 반올림, %) 출력.
 - 잘못된 입력 시 IllegalArgumentException 발생, 메시지는 [ERROR]로 시작, 해당 단계부터 재입력.

## 예외 규칙
 - 구매 금액이 1,000 단위가 아님 / 0 이하
 - 로또 번호 개수 ≠ 6, 범위(1~45) 밖, 중복
 - 보너스 범위 밖, 당첨 번호와 중복
 - 메시지 예: [ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.
