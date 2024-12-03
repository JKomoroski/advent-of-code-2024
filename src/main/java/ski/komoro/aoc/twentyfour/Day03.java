package ski.komoro.aoc.twentyfour;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day03 extends AOCBase {

    static final Pattern DONT_PATTERN = Pattern.compile("don't\\(\\).*?do\\(\\)");
    static final Pattern MUL_PATTERN = Pattern.compile("mul\\((?<firstOperand>\\d{1,3}),(?<secondOperand>\\d{1,3})\\)");
    public static void main(final String[] args) throws Exception {
        new Day03().run();
    }

    Day03() {
        super("day-3", "in.txt");
    }

    @Override
    void part1(final Stream<String> fileInput) throws Exception {
        final var sumOfMuls = Day03.collectAsSingleString(fileInput)
                .flatMap(s -> MUL_PATTERN.matcher(s).results())
                .mapToInt(Day03::processMul)
                .sum();

        System.out.println("Part 1: " + sumOfMuls);
    }

    @Override
    void part2(final Stream<String> fileInput) throws Exception {
        final var sumOfMuls = Day03.collectAsSingleString(fileInput)
                .map(s -> DONT_PATTERN.split(s, 0))
                .flatMap(Arrays::stream)
                .flatMap(s -> MUL_PATTERN.matcher(s).results())
                .mapToInt(m -> processMul(m))
                .sum();

        System.out.println("Part 2: " + sumOfMuls);

    }

    // Shakes fist!!!!!!!!!! multiline splits really messed me up.
    static Stream<String> collectAsSingleString(final Stream<String> fileInput) {
        return Stream.of(fileInput.collect(Collectors.joining()));
    }

    static int processMul(MatchResult r) {
        return Integer.parseInt(r.group("firstOperand")) * Integer.parseInt(r.group("secondOperand"));
    }
}
