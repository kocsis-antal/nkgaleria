package xyz.kocsisantal.pdf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class GeneratorTest {

    public static Stream<Arguments> findSplitPointSource() {
        return Stream.of(
                Arguments.of("", 100, 0),
                Arguments.of("", 0, 0),

                Arguments.of("qwerty", 100, 6),
                Arguments.of("qwerty", 80, 6),
                Arguments.of("qwerty", 60, 6),
                Arguments.of("qwerty", 40, 6),
                Arguments.of("qwerty", 20, 6),
                Arguments.of("qwerty", 0, 6),

                Arguments.of("qwer ty", 100, 7),
                Arguments.of("qwer ty", 80, 4),
                Arguments.of("qwer ty", 60, 4),
                Arguments.of("qwer ty", 40, 4),
                Arguments.of("qwer ty", 20, 4),
                Arguments.of("qwer ty", 0, 4),

                Arguments.of("qw erty", 100, 7),
                Arguments.of("qw erty", 80, 7),
                Arguments.of("qw erty", 60, 2),
                Arguments.of("qw erty", 40, 2),
                Arguments.of("qw erty", 20, 2),
                Arguments.of("qw erty", 0, 2),

                Arguments.of("qwer t y", 100, 8),
                Arguments.of("qwer t y", 80, 6),
                Arguments.of("qwer t y", 60, 4),
                Arguments.of("qwer t y", 40, 4),
                Arguments.of("qwer t y", 20, 4),
                Arguments.of("qwer t y", 0, 4),

                Arguments.of("qwe rt y", 100, 8),
                Arguments.of("qwe rt y", 80, 6),
                Arguments.of("qwe rt y", 60, 3),
                Arguments.of("qwe rt y", 40, 3),
                Arguments.of("qwe rt y", 20, 3),
                Arguments.of("qwe rt y", 0, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("findSplitPointSource")
    void findSplitPoint(String source, Integer percent, int expexted) {
        final int splitPoint = Generator.findSplitPoint(source, percent);
        Assertions.assertEquals(expexted, splitPoint);
    }
}