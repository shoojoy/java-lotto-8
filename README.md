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

## 기능 구현 순서
 ### 세팅
 - [x] java -version을 통하여 java21인지 확인 / ./gradlew clean test 기본 동작 확인
 ### 도메인 및 상수 정의
 - [x] 패키지 구성: lotto.domain, lotto.view, lotto.util, 시작점 Application
 - [x] 공용 상수 정의 (예: PRICE_PER_TICKET = 1000, MIN=1, MAX=45, COUNT=6)
 ### 구매 금액
 - [x] 생성 시 1,000원 단위,양수 검증
 - [x] count()로 발행 장수 반환 (amount / PRICE_PER_TICKET)
 ### 티켓
 - [x] 생성자에서 개수=6, 범위 1~45, 중복 없음 검증
 - [x] 내부 번호 오름차순 정렬 및 불변화
 ### NumberParser
 - [x] "1,2,3,4,5,6" → List<Integer> 변환(공백 trim, 숫자만 허용)
 - [x] 포맷 오류 시 IllegalArgumentException("[ERROR] ...") 발생
 ### 발행기
 - [x] issue(n): Randoms.pickUniqueNumbersInRange(1,45,6)로 n장 생성
 - [x] 생성된 리스트를 Lotto로 감싸 정렬/검증 일원화
 ### 당첨 정보 
 - [x] 당첨 번호 6개는 Lotto 재사용(검증 공유)
 - [x] 보너스 1개: 범위 1~45, 당첨 번호와 중복 불가
 ### 등수 규칙
 - [x] 상수/필드 정의: (matchCount, needBonus, prize)
   - 1등(6,false, 2_000_000_000), 2등(5,true, 30_000_000), 3등(5,false, 1_500_000), 4등(4,false, 50_000), 5등(3,false, 5_000), MISS
 - [x] of (int matchCount, boolean bonusMatched) 구현(조기 return, else/switch 금지)
 ### 매칭
 - [x] match (Lotto ticket, WinningNumbers winning) 구현
 ### 집계/수익률 
 - [x] Map<Rank,Integer>로 등수별 개수 집계(MISS 제외 출력)
 - [x] totalPrize = Σ(count*prize), totalCost = tickets*PRICE_PER_TICKET
 - [x] yieldPercentageRounded() 구현: (totalPrize/totalCost*100) → 소수점 둘째 자리 반올림 후 한 자리 표시
 ### 출력
 - [ ] 발행 결과 출력: N개를 구매했습니다. + 각 티켓 번호(오름차순)
 - [ ] 통계 표 포맷 출력
 - [ ] 총 수익률 출력: 총 수익률은 Y%입니다. (금액 천 단위 콤마, 퍼센트 한 자리)
 ### 입력/재시도
 - [ ] 구매 금액 입력 및 검증(실패 시 [ERROR] 후 해당 단계부터 재입력)
 - [ ] 당첨 번호(6개) 입력/파싱/검증(실패 시 재입력)
 - [ ] 보너스 번호(1개) 입력/검증(실패 시 재입력)
 ### 흐름 조립
 - [ ] 구매 금액 -> 장수 계산 -> 티켓 발행/출력
 - [ ] 당첨 번호 + 보너스 입력/검증
 - [ ] 모든 티켓 매칭 -> Result 집계 -> 통계/수익률 출력