# Лаб 4: Нэгжийн тестийн эхлэл — JUnit 5

**Нэр:** Зоригтбаатар
**Код:** B242270078
**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест (2026)

## Хувилбарууд

### java -version
```
openjdk version "17.0.21.1" 2026-11-17
OpenJDK Runtime Environment (build 17.0.21.1+2-1-26.04.1-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.21.1+2-1-26.04.1-Ubuntu, mixed mode, sharing)
```

### mvn -version
```
[1mApache Maven 3.9.12[m
Maven home: /usr/share/maven
Java version: 17.0.21.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "7.0.0-43-generic", arch: "amd64", family: "unix"
```

## Төслийн тухай

Maven төсөл (`mn.edu.must.sqat:lab04-junit`), JUnit 5 (Jupiter 5.10.2), Java 17. Тестлэж буй `GradeCalculator` класс нь хоёр метод агуулна:

- `letterGrade(score)`: 0–100 оноог үсгэн дүн болгоно (90+ A, 80–89 B, 70–79 C, 60–69 D, 60-аас доош F). Хязгаараас гарсан оноонд `IllegalArgumentException` шиднэ.
- `totalScore(att, lab, quiz1, quiz2, exam)`: ирц (10), лаб+бие даалт (40), сорил 1 (10), сорил 2 (10), шалгалт (30)-ийн нийлбэр. Сөрөг эсвэл дээд хязгаараасаа хэтэрсэн утгад `IllegalArgumentException` шиднэ.

Тестүүд нь `src/test/java/mn/edu/must/sqat/GradeCalculatorTest.java` файлд байна. Ажиллуулах:

```bash
mvn test
```

## Тестүүд

| Төрөл | Тест | Юуг шалгах |
|---|---|---|
| Хязгаар | `ninetyIsExactlyA` | 90 → A |
| Хязгаар | `eightyNinePointNinetyNineIsB` | 89.99 → B |
| Хязгаар | `sixtyIsExactlyD` | 60 → D |
| Хязгаар | `fiftyNinePointNinetyNineIsF` | 59.99 → F |
| Хязгаар | `zeroIsF`, `hundredIsA` | 0 → F, 100 → A |
| Ердийн | `typicalValues` | 95→A, 85→B, 75→C, 65→D, 30→F |
| Алдаа | `letterGradeRejectsOutOfRange` | -1 ба 101 exception шиднэ |
| totalScore | `totalScoreMaximumIs100` | Бүх оноо дээд хэмжээнд байхад нийлбэр 100 |
| totalScore | `totalScoreRejectsNegativeAttendance` | Сөрөг ирцэд exception |
| totalScore | `totalScoreRejectsLabAboveMax` | Лаб 41 гэхэд exception |
| Parameterized | `letterGradeBoundaries` | Хязгаарын 11 утга (`@CsvSource`) |
| Parameterized | `letterGradeRejectsInvalid` | Хүчингүй 5 оноо (`@ValueSource`) |
| Parameterized | `totalScoreSums` | Нийлбэрийн 4 тохиолдол (`@CsvSource`) |

Тест бүр Arrange–Act–Assert бүтэцтэй, `@DisplayName`-тэй.

## Тестийн тоо

- Тестийн метод (`@Test` болон `@ParameterizedTest`): **14**
- `results/mvn-test.txt`-ийн сүүлийн мөр: **Tests run: 31, Failures: 0, Errors: 0, Skipped: 0**, BUILD SUCCESS
- Метод (14) ба `Tests run` (31) тоо зөрөх шалтгаан: Surefire нь `@CsvSource`, `@ValueSource`-ийн мөр бүрийг тусдаа тест гэж тоолдог (11 энгийн тест + 11 + 5 + 4 parameterized мөр = 31).

## Мутацийн үр дүн

`letterGrade` доторх `score >= 90` нөхцөлийг зориуд `score > 90` болгож `mvn test` ажиллуулсан (`results/mvn-test-mutant.txt`).

- Үр дүн: Tests run: 31, Failures: 2, BUILD FAILURE
- Унасан тестүүд:

```
[[1;31mERROR[m] [1;31m  GradeCalculatorTest.letterGradeBoundaries:121 expected: <A> but was: <B>[m
[[1;31mERROR[m] [1;31m  GradeCalculatorTest.ninetyIsExactlyA:20 expected: <A> but was: <B>[m
```

- Дараа нь нөхцөлийг `>= 90` болгож буцааж засаад `mvn test`-ийг дахин ажиллуулахад бүх 31 тест ногоон болсон (`results/mvn-test.txt`).

## Дүгнэлт

Энэ лабораторид би `GradeCalculator` классын `letterGrade` ба `totalScore` методыг JUnit 5-аар шалгасан бөгөөд 14 тестийн метод бичсэн. Surefire parameterized тестийн мөр бүрийг тусад нь тоодог тул `mvn test` нийт 31 тест ажиллуулж, бүгд ногоон болсон. Тест бүрийг Arrange–Act–Assert бүтэцтэй бичиж, `@DisplayName`-ээр юуг шалгаж байгааг нь ойлгомжтой болгосон бөгөөд хязгаарын утгуудад (90, 89.99, 60, 59.99, 0, 100) онцгой анхаарсан. Parameterized тестээр нэг тестийн кодоор олон оноог шалгаж, ижил код давтахгүй байж чадсан. Мутацийн туршилтад `score >= 90`-ийг `score > 90` болгоход `ninetyIsExactlyA` болон `letterGradeBoundaries` гэсэн 2 тест унаж, `expected: <A> but was: <B>` гэсэн мессеж өгсөн. Яг 90 оноо нь `>` нөхцөлд A-д орохгүй B-д орох тул зөвхөн яг 90 дээр хариу өөрчлөгдсөн, 95 эсвэл 85 гэх мэт ердийн утгатай тестүүд энэ өөрчлөлтийг мэдээгүй. Үүнээс би тестийн тоо биш, тест хязгаарын утгыг шалгаж байгаа эсэх нь чухал гэдгийг ойлгосон бөгөөд мутацийг буцааж `>= 90` болгоход бүх 31 тест дахин ногоон болсон. Дараагийн удаа PIT гэх мэт хэрэгслээр ийм мутацийг автоматаар үүсгэж, өөрийн тестийнхээ хүчийг шалгаж үзмээр байна.
