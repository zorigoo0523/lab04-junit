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
}
