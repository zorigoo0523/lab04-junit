package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class GradeCalculatorTest {

    // ---------- letterGrade: хязгаарын утгууд ----------

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        String grade = calc.letterGrade(90.0);             // Act
        assertEquals("A", grade);                          // Assert
    }

    @Test
    @DisplayName("89.99 оноо B дүн байх ёстой (A-аас жаахан дутуу)")
    void eightyNinePointNinetyNineIsB() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(89.99);
        assertEquals("B", grade);
    }

    @Test
    @DisplayName("60 оноо яг D дүн байх ёстой (хязгаарын тохиолдол)")
    void sixtyIsExactlyD() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(60.0);
        assertEquals("D", grade);
    }

    @Test
    @DisplayName("59.99 оноо F дүн байх ёстой (D-ээс жаахан дутуу)")
    void fiftyNinePointNinetyNineIsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(59.99);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("0 оноо F дүн байх ёстой (доод хязгаар)")
    void zeroIsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(0.0);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("100 оноо A дүн байх ёстой (дээд хязгаар)")
    void hundredIsA() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(100.0);
        assertEquals("A", grade);
    }

    // ---------- letterGrade: ердийн ба буруу оролт ----------

    @Test
    @DisplayName("Ердийн оноонууд зөв дүн өгнө: 95→A, 85→B, 75→C, 65→D, 30→F")
    void typicalValues() {
        GradeCalculator calc = new GradeCalculator();
        assertAll(
            () -> assertEquals("A", calc.letterGrade(95)),
            () -> assertEquals("B", calc.letterGrade(85)),
            () -> assertEquals("C", calc.letterGrade(75)),
            () -> assertEquals("D", calc.letterGrade(65)),
            () -> assertEquals("F", calc.letterGrade(30))
        );
    }

    @Test
    @DisplayName("0-100-аас гадна оноо (-1, 101) exception шиднэ")
    void letterGradeRejectsOutOfRange() {
        GradeCalculator calc = new GradeCalculator();
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1)),
            () -> assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101))
        );
    }

    // ---------- totalScore ----------

    @Test
    @DisplayName("Бүх оноо дээд хэмжээндээ байхад нийлбэр 100 гарна")
    void totalScoreMaximumIs100() {
        GradeCalculator calc = new GradeCalculator();
        double total = calc.totalScore(10, 40, 10, 10, 30);
        assertEquals(100.0, total, 0.0001);
    }

    @Test
    @DisplayName("Сөрөг ирц (att = -5) exception шиднэ")
    void totalScoreRejectsNegativeAttendance() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class,
            () -> calc.totalScore(-5, 40, 10, 10, 30));
    }

    @Test
    @DisplayName("Лаб+бие даалт дээд хязгаараас хэтэрвэл (lab = 41) exception шиднэ")
    void totalScoreRejectsLabAboveMax() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(IllegalArgumentException.class,
            () -> calc.totalScore(10, 41, 10, 10, 30));
    }

    // ---------- Parameterized тестүүд ----------

    @ParameterizedTest(name = "оноо={0} → дүн={1}")
    @DisplayName("letterGrade: хязгаарын утгууд олон тохиолдолд")
    @CsvSource({"95,A", "90,A", "89.99,B", "80,B", "79.99,C", "70,C",
                "69.99,D", "60,D", "59.99,F", "0,F", "100,A"})
    void letterGradeBoundaries(double score, String expected) {
        assertEquals(expected, new GradeCalculator().letterGrade(score));
    }

    @ParameterizedTest(name = "letterGrade({0}) exception шиднэ")
    @DisplayName("letterGrade: хүчингүй оноонууд exception шиднэ")
    @ValueSource(doubles = {-1, -0.01, 100.01, 101, 1000})
    void letterGradeRejectsInvalid(double score) {
        assertThrows(IllegalArgumentException.class,
            () -> new GradeCalculator().letterGrade(score));
    }

    @ParameterizedTest(name = "{0}+{1}+{2}+{3}+{4} = {5}")
    @DisplayName("totalScore: нийлбэрийг олон тохиолдолд шалгана")
    @CsvSource({"10,40,10,10,30,100", "0,0,0,0,0,0",
                "5,20,5,5,15,50", "9,35,8,7,25,84"})
    void totalScoreSums(double att, double lab, double q1, double q2, double exam, double expected) {
        assertEquals(expected, new GradeCalculator().totalScore(att, lab, q1, q2, exam), 0.0001);
    }
}
