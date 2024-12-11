package ski.komoro.aoc.twentyfour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day07 extends AOCBase {


    public static void main(final String[] args) throws Exception {
        new Day07().run();
    }

    private Day07() {
        super("day-7", "in.txt");
    }

    @Override
    protected void part1(final Stream<String> fileInput) throws Exception {
        final var sumOfPossible = fileInput.map(Problem::parse)
                .filter(p -> p.findPossiblePart1Solutions() > 0)
                .mapToLong(Problem::solution)
                .sum();
        System.out.println("Part 1: " + sumOfPossible);
    }

    @Override
    protected void part2(final Stream<String> fileInput) throws Exception {
        final var sumOfPossible = fileInput.map(Problem::parse)
                .filter(p -> p.findPossiblePart2Solutions() > 0)
                .mapToLong(Problem::solution)
                .sum();
        System.out.println("Part 2: " + sumOfPossible);
    }

    record Problem(long solution, long[] operands) {
        static Problem parse(String line) {
            final var split = line.split(" ");
            final var solution = split[0].replace(":", "");
            final var operands = Arrays.stream(split)
                    .skip(1)
                    .mapToLong(Long::parseLong)
                    .toArray();
            return new Problem(Long.parseLong(solution), operands);
        }

        long findPossiblePart1Solutions() {
            long solutions = 0;
            final var operations = List.of(add, mult);
            final var combinations = generateCombinations(operations, operands.length - 1);
            for (final var combination : combinations) {
                long result = operands[0];

                for (var i = 1; i < operands.length; i++) {
                    final var f = combination.get(i - 1);
                    result = f.apply(result, operands[i]);
                }

                if(result == solution) {
                    solutions++;
                }
            }
            return solutions;
        }

        long findPossiblePart2Solutions() {
            long solutions = 0;
            final var operations = List.of(add, mult, concat);
            final var combinations = generateCombinations(operations, operands.length - 1);
            for (final var combination : combinations) {
                long result = operands[0];

                for (var i = 1; i < operands.length; i++) {
                    final var f = combination.get(i - 1);
                    result = f.apply(result, operands[i]);
                }

                if(result == solution) {
                    solutions++;
                }
            }
            return solutions;
        }

    }

    static BiFunction<Long, Long, Long> add = Math::addExact;
    static BiFunction<Long, Long, Long> mult = Math::multiplyExact;
    static BiFunction<Long, Long, Long> concat = (l1, l2) -> Long.parseLong(l1.toString() + l2.toString());


    public static <T> List<List<T>> generateCombinations(Collection<T> values, long size) {
        List<List<T>> result = new ArrayList<>();
        generateCombinationsHelper(values, size, new ArrayList<>(), result);
        return result;
    }

    private static <T> void generateCombinationsHelper(Collection<T> values, long size, List<T> current, List<List<T>> result) {
        if (current.size() == size) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (T value : values) {
            current.add(value);
            generateCombinationsHelper(values, size, current, result);
            current.remove(current.size() - 1); // Backtrack
        }
    }
}
