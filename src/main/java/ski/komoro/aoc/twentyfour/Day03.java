package ski.komoro.aoc.twentyfour;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import ski.komoro.aoc.utils.Utils;

final class Day03 extends AOCBase {

    static final Pattern DONT_PATTERN = Pattern.compile("don't\\(\\).*?do\\(\\)");
    static final Pattern MUL_PATTERN = Pattern.compile("mul\\((?<firstOperand>\\d{1,3}),(?<secondOperand>\\d{1,3})\\)");

    public static void main(final String[] args) throws Exception {
        new Day03().run();
    }

    private Day03() {
        super("day-3", "in.txt");
    }

    @Override
    protected void part1(final Stream<String> fileInput) throws Exception {
        final var sumOfMuls = fileInput
                .map(MUL_PATTERN::matcher)
                .flatMap(Matcher::results)
                .mapToInt(Day03::processMul)
                .sum();

        System.out.println("Part 1: " + sumOfMuls);
    }

    @Override
    protected void part2(final Stream<String> fileInput) throws Exception {
        // :shaking_fist: multiline splits really messed me up.
        final var sumOfMuls = Utils.joinStrings(fileInput)
                .map(s -> DONT_PATTERN.split(s, 0))
                .flatMap(Arrays::stream)
                .map(MUL_PATTERN::matcher)
                .flatMap(Matcher::results)
                .mapToInt(Day03::processMul)
                .sum();

        System.out.println("Part 2: " + sumOfMuls);

    }



    static int processMul(MatchResult r) {
        return Integer.parseInt(r.group("firstOperand")) * Integer.parseInt(r.group("secondOperand"));
    }
}
