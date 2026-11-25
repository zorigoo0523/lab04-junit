# Лаб 4: Нэгжийн тестийн эхлэл — JUnit 5

**Нэр:** Зоригтбаатар
**Код:** B242270078
**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест (2026)

## Хувилбарууд

### java -version

### mvn -version

## Төслийн тухай

`GradeCalculator` класс нь хоёр метод агуулна:
- `letterGrade(score)`: 0–100 оноог үсгэн дүн болгоно (90+ A, 80–89 B, 70–79 C, 60–69 D, <60 F). Хязгаараас гарвал `IllegalArgumentException` шиднэ.
- `totalScore(att, lab, quiz1, quiz2, exam)`: ирц(10), лаб+бие даалт(40), сорил1(10), сорил2(10), шалгалт(30)-ийн нийлбэр. Сөрөг эсвэл дээд хязгаараас хэтэрсэн утгад `IllegalArgumentException` шиднэ.

## Тестийн тоо

- Тестийн метод (@Test болон @ParameterizedTest): **14**
- results/mvn-test.txt-ийн сүүлийн мөр: **Tests run: 31, Failures: 0, Errors: 0, Skipped: 0**, BUILD SUCCESS
- Метод ба Tests run тоо зөрөх шалтгаан: Surefire нь `@CsvSource`, `@ValueSource`-ийн мөр бүрийг тусдаа тест гэж тоолдог.

## Мутацийн үр дүн

`letterGrade` доторх `score >= 90` нөхцөлийг зориуд `score > 90` болгож `mvn test` ажиллуулсан (`results/mvn-test-mutant.txt`).

- Үр дүн: Tests run: 31, Failures: 2, BUILD FAILURE
- Унасан тестүүд:
```
GradeCalculatorTest.letterGradeBoundaries:121 expected: <A> but was: <B>
GradeCalculatorTest.ninetyIsExactlyA:20 expected: <A> but was: <B>
```
- Дараа нь нөхцөлийг `>= 90` болгож буцааж засаад `mvn test`-ийг дахин ажиллуулахад бүх тест ногоон болсон (`results/mvn-test.txt`).

## Дүгнэлт

Энэ лабораторид GradeCalculator классын letterGrade ба totalScore методыг JUnit 5-аар шалгасан. Нийт 14 тестийн метод бичсэн бөгөөд Surefire parameterized тестийн мөр бүрийг тусад нь тоодог тул results/mvn-test.txt-д Tests run: 31 гарсан. Тест бүрийг Arrange–Act–Assert бүтэцтэй, ойлгомжтой @DisplayName-тэй бичиж, хязгаарын утгуудыг (90, 89.99, 60, 59.99, 0, 100) энгийн ба parameterized хэлбэрээр хоёуланг нь шалгасан. Мутацийн туршилтад score >= 90-ийг score > 90 болгоход BUILD FAILURE гарч, 2 тест унасан нь 90 → A хязгаарыг шалгадаг тест үнэхээр ажиллаж байгааг нотолсон. Хамгийн сонирхолтой нь ердөө нэг тэмдэгтийн өөрчлөлт зөвхөн яг 90 дээр л алдаа үүсгэдэг, 95 эсвэл 85 гэх мэт ердийн утгууд үүнийг илрүүлэхгүй байсан явдал. Иймээс бүх тест ногоон байсан ч хязгаарын утгыг шалгаагүй бол энэ алдааг барьж чадахгүй байсан. Мутацийг буцааж засахад тестүүд дахин ногоон болсон. Дараагийн лабораторид ийм мутацийг PIT-ээр автоматаар үүсгэж шалгана.
