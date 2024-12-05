package ski.komoro.aoc.twentyfour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05 extends AOCBase {


    public static void main(final String[] args) throws Exception {
        new Day05().run();
    }

    private Day05() {
        super("day-5", "in.txt");
    }

    @Override
    protected void part1(final Stream<String> fileInput) throws Exception {
        Map<Integer, Set<Integer>> rules = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();
        fileInput.iterator().forEachRemaining(line -> {
            if (line.contains("|")) {
                final var split = line.split("\\|");
                final var k = Integer.parseInt(split[0]);
                final var r = Integer.parseInt(split[1]);
                final var v = rules.get(k);
                if(v == null) {
                    final var s = new HashSet<Integer>();
                    s.add(r);
                    rules.put(k, s);
                } else {
                    v.add(r);
                }
            }
            if (line.contains(",")) {
                final var split = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .toList();
                updates.add(split);
            }
        });

        long sumOfCorrectMiddles = updates.stream()
                .filter(u -> {
                    for (var i = 0; i < u.size(); i++) {
                        final var rule = u.get(i);
                        final var after = rules.get(rule);
                        if(after == null) continue;
                        for (var j = i; j >= 0; j--) {
                            if (after.contains(u.get(j))) {
                                return false;
                            }
                        }
                    }
                    return true;
                })
                .mapToLong(u -> u.get(u.size()/2))
                .sum();

        System.out.println("Part 1: " + sumOfCorrectMiddles);
    }

    @Override
    protected void part2(final Stream<String> fileInput) throws Exception {
        Map<Integer, Set<Integer>> rules = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();
        fileInput.iterator().forEachRemaining(line -> {
            if (line.contains("|")) {
                final var split = line.split("\\|");
                final var k = Integer.parseInt(split[0]);
                final var r = Integer.parseInt(split[1]);
                final var v = rules.get(k);
                if(v == null) {
                    final var s = new HashSet<Integer>();
                    s.add(r);
                    rules.put(k, s);
                } else {
                    v.add(r);
                }
            }
            if (line.contains(",")) {
                final var split = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                updates.add(split);
            }
        });

        final var ruleComparator = new RuleComparator(rules);
        long sumOfFixedMiddles = updates.stream()
                .filter(u -> {
                    for (var i = 0; i < u.size(); i++) {
                        final var rule = u.get(i);
                        final var after = rules.get(rule);
                        if(after == null) continue;
                        for (var j = i; j >= 0; j--) {
                            if (after.contains(u.get(j))) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .map(u -> {
                    u.sort(ruleComparator);
                    return u;
                })
                .mapToLong(u -> u.get(u.size()/2))
                .sum();

        System.out.println("Part 2: " + sumOfFixedMiddles);
    }

    class RuleComparator implements Comparator<Integer> {
        private final Map<Integer, Set<Integer>> rules;
        RuleComparator(final Map<Integer, Set<Integer>> rules) {
            this.rules = rules;
        }

        @Override
        public int compare(final Integer o1, final Integer o2) {
            final var v = rules.get(o1);
            if(v == null) return 0;
            if(v.contains(o2)) return -1;
            return 1;
        }
    }
}
