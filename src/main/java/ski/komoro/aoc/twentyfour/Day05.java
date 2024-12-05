package ski.komoro.aoc.twentyfour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import ski.komoro.aoc.utils.Tuple;

public class Day05 extends AOCBase {


    public static void main(final String[] args) throws Exception {
        new Day05().run();
    }

    private Day05() {
        super("day-5", "in.txt");
    }

    @Override
    protected void part1(final Stream<String> fileInput) throws Exception {
        Set<Tuple<Integer, Integer>> rules = new HashSet<>();
        List<List<Integer>> updates = new ArrayList<>();
        parse(fileInput, rules, updates);

        final var correctFilter = isCorrect(rules);
        long sumOfCorrectMiddles = updates.stream()
                .filter(correctFilter)
                .mapToLong(u -> u.get(u.size() / 2))
                .sum();

        System.out.println("Part 1: " + sumOfCorrectMiddles);
    }

    @Override
    protected void part2(final Stream<String> fileInput) throws Exception {
        Set<Tuple<Integer, Integer>> rules = new HashSet<>();
        List<List<Integer>> updates = new ArrayList<>();
        parse(fileInput, rules, updates);

        final var incorrectFilter = Predicate.not(isCorrect(rules));
        final Comparator<Integer> ruleComparator = (o1, o2) -> rules.contains(Tuple.of(o1, o2)) ? -1 : 1;

        long sumOfFixedMiddles = updates.stream()
                .filter(incorrectFilter)
                .map(u -> {
                    u.sort(ruleComparator);
                    return u;
                })
                .mapToLong(u -> u.get(u.size() / 2))
                .sum();

        System.out.println("Part 2: " + sumOfFixedMiddles);
    }

    private void parse(Stream<String> fileInput, final Set<Tuple<Integer, Integer>> rules, final List<List<Integer>> updates) {
        fileInput.forEach(line -> {
            if (line.contains("|")) {
                final var split = line.split("\\|");
                rules.add(Tuple.of(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }

            if (line.contains(",")) {
                final var split = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                updates.add(split);
            }
        });
    }

    private Predicate<List<Integer>> isCorrect(final Set<Tuple<Integer, Integer>> rules) {
        return u -> IntStream.range(0, u.size())
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(0, i).mapToObj(j -> Tuple.of(u.get(i), u.get(j))))
                .noneMatch(rules::contains);
    }
}
