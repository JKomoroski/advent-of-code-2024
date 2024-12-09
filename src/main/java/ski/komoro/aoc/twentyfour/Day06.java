package ski.komoro.aoc.twentyfour;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import ski.komoro.aoc.utils.Tuple;

public class Day06 extends AOCBase {


    public static void main(final String[] args) throws Exception {
        new Day06().run();
    }

    private Day06() {
        super("day-6", "in.txt");
    }

    @Override
    protected void part1(final Stream<String> fileInput) throws Exception {
        final var l = fileInput.toList();
        final char[][] map = new char[l.size()][l.getFirst().length()];
        final char[][] trail = new char[l.size()][l.getFirst().length()];

        for (var i = 0; i < l.size(); i++) {
            map[i] = l.get(i).toCharArray();
            trail[i] = l.get(i).toCharArray();
        }

        final var start = generateCoordinates(map[0].length, map.length)
                .filter(c -> isGuard(map, c))
                .findFirst()
                .orElseThrow();
        var prev = new Tuple<>(start._1(), start._2() + 1);
        var current = start;
        do {
            final var next = isObstruction(map, nextStraight(current, prev))
                    ? nextRight(current, prev)
                    : nextStraight(current, prev);

            trail[current._2()][current._1()] = 'X';

            prev = current;
            current = next;
        } while (!isOffMap(map, current));

        final var count = generateCoordinates(trail.length, trail[0].length)
                .filter(c -> isTrail(trail, c))
                .count();

        System.out.println("Part 1: " + count);
    }

    @Override
    protected void part2(final Stream<String> fileInput) throws Exception {
        final var l = fileInput.toList();
        final char[][] map = new char[l.size()][l.getFirst().length()];
        final char[][] trail = new char[l.size()][l.getFirst().length()];

        for (var i = 0; i < l.size(); i++) {
            map[i] = l.get(i).toCharArray();
            trail[i] = l.get(i).toCharArray();
        }

        final var start = generateCoordinates(map[0].length, map.length)
                .filter(c -> isGuard(map, c))
                .findFirst()
                .orElseThrow();
        var prev = new Tuple<>(start._1(), start._2() + 1);
        var current = start;
        do {
            final var next = isObstruction(map, nextStraight(current, prev))
                    ? nextRight(current, prev)
                    : nextStraight(current, prev);

            trail[current._2()][current._1()] = 'X';

            prev = current;
            current = next;
        } while (!isOffMap(map, current));

        final var path = generateCoordinates(trail.length, trail[0].length)
                .filter(c -> isTrail(trail, c))
                .collect(Collectors.toSet());

        long loopPotential = 0L;
        for (final var candidate : path) {
            map[candidate._2()][candidate._1()] = '#';

            prev = new Tuple<>(start._1(), start._2() + 1);
            current = start;
            var currentPath = new HashSet<PathWithDirection>();
            var dir = 0;

            do {
                currentPath.add(new PathWithDirection(current._1(), current._2(), dir));

                final var s = nextStraight(current, prev);
                var r = nextRight(current, prev);

                // nasty double right case.
                if (isObstruction(map, r) && isObstruction(map, s)) {
                    r = prev;
                    dir = nextDir(dir);
                }

                final var next = isObstruction(map, s)
                        ? r
                        : s;

                if (next.equals(r)) {
                    dir = nextDir(dir);
                }

                if (currentPath.contains(new PathWithDirection(next._1(), next._2(), dir))) {
                    loopPotential++;
                    break;
                }

                prev = current;
                current = next;
            } while (!isOffMap(map, current));

            map[candidate._2()][candidate._1()] = '.';
        }

        System.out.println("Part 2: " + loopPotential);
    }

    Stream<Tuple<Integer, Integer>> generateCoordinates(int x, int y) {
        return IntStream.range(0, x)
                .boxed()
                .flatMap(i -> IntStream.range(0, y).boxed().map(j -> Tuple.of(i, j)));
    }

    private Tuple<Integer, Integer> nextRight(Tuple<Integer, Integer> current, Tuple<Integer, Integer> prev) {

        if (Objects.equals(prev._1(), current._1())) { // n or s
            int y = current._2();
            int x = current._2() < prev._2() ? current._1() + 1 : current._1() - 1;
            return new Tuple<>(x, y);
        }

        if (Objects.equals(prev._2(), current._2())) { // e or w
            int x = current._1();
            int y = current._1() < prev._1() ? current._2() - 1 : current._2() + 1;
            return new Tuple<>(x, y);
        }

        throw new IllegalArgumentException();

    }

    private Tuple<Integer, Integer> nextStraight(Tuple<Integer, Integer> current, Tuple<Integer, Integer> prev) {
        int x = Objects.equals(prev._1(), current._1())
                ? current._1()
                : current._1() + (current._1() - prev._1());
        int y = Objects.equals(prev._2(), current._2())
                ? current._2()
                : current._2() + (current._2() - prev._2());

        return new Tuple<>(x, y);
    }

    int nextDir(int dir) {
        return switch (dir) {
            case 0 -> 1; // n -> e
            case 1 -> 2; // e -> s
            case 2 -> 3; // s -> w
            case 3 -> 0; // w -> n
            default -> throw new IllegalArgumentException();
        };
    }

    private boolean isTrail(char[][] map, Tuple<Integer, Integer> coordinate) {
        return map[coordinate._2()][coordinate._1()] == 'X';
    }

    private boolean isGuard(char[][] map, Tuple<Integer, Integer> coordinate) {
        return map[coordinate._2()][coordinate._1()] == '^';
    }

    private boolean isObstruction(char[][] map, Tuple<Integer, Integer> coordinate) {
        try {
            return map[coordinate._2()][coordinate._1()] == '#';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean isOffMap(char[][] map, Tuple<Integer, Integer> coordinate) {
        try {
            char c = map[coordinate._2()][coordinate._1()];
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    static class PathWithDirection {

        public PathWithDirection(final int x, final int y, final int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        private final int x;
        private final int y;
        private final int direction;

        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof final PathWithDirection that)) {
                return false;
            }
            return x == that.x && y == that.y && direction == that.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }
    }

}
