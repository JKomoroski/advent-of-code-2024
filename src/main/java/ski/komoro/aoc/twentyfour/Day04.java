package ski.komoro.aoc.twentyfour;

import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import ski.komoro.aoc.utils.Tuple;

final class Day04 extends AOCBase {

    static final Pattern XMAS = Pattern.compile("XMAS|SAMX");
    static final Pattern MAS = Pattern.compile("MAS|SAM");

    public static void main(final String[] args) throws Exception {
        new Day04().run();
    }

    private Day04() {
        super("day-4", "in.txt");
    }

    @Override
    protected void part1(final Stream<String> fileInput) throws Exception {

        final var l = fileInput.toList();
        final char[][] chars = new char[l.size()][l.getFirst().length()];

        for (var i = 0; i < l.size(); i++) {
            chars[i] = l.get(i).toCharArray();
        }

        long sum = search(chars);

        System.out.println("Part 1: " + sum);
        // 2546 wrong

    }

    @Override
    protected void part2(final Stream<String> fileInput) throws Exception {
        final var l = fileInput.toList();
        final char[][] chars = new char[l.size()][l.getFirst().length()];

        for (var i = 0; i < l.size(); i++) {
            chars[i] = l.get(i).toCharArray();
        }

        long sum = search2(chars);

        System.out.println("Part 2: " + sum);
    }

    Stream<Tuple<Integer, Integer>> generateCoordinates(int x, int y) {
        return IntStream.range(0, x)
                .boxed()
                .flatMap(i -> IntStream.range(0, y).boxed().map(j -> Tuple.of(i, j)));
    }

    long search2(char[][] arr) {
        return generateCoordinates(arr.length, arr[0].length)
                .filter(c -> checkMas(arr, c._1(), c._2()))
                .count();
    }

    long search(char[][] arr) {
        return generateCoordinates(arr.length, arr[0].length)
                .flatMap(c -> mapToMatches(arr, c))
                .filter(Boolean::booleanValue)
                .count();

    }

    private Stream<Boolean> mapToMatches(final char[][] arr, Tuple<Integer, Integer> c) {
        return Stream.of(
                checkHorizontal(arr, c._1(), c._2()),
                checkVertical(arr, c._1(), c._2()),
                checkTopLeftToBottomRight(arr, c._1(), c._2()),
                checkBottomLeftToTopRight(arr, c._1(), c._2())
        );
    }

    boolean checkMas(char[][] arr, int x, int y) {
        try {

            var diag1 = String.valueOf(arr[x - 1][y - 1])
                    + arr[x][y]
                    + arr[x + 1][y + 1];
            var diag2 = String.valueOf(arr[x - 1][y + 1])
                    + arr[x][y]
                    + arr[x + 1][y - 1];
            return MAS.matcher(diag1).matches() && MAS.matcher(diag2).matches();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean checkHorizontal(char[][] arr, int x, int y) {
        try {
            final var s = String.valueOf(arr[x][y])
                    + arr[x][y + 1]
                    + arr[x][y + 2]
                    + arr[x][y + 3];
            return XMAS.matcher(s).matches();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean checkVertical(char[][] arr, int x, int y) {
        try {
            final var s = String.valueOf(arr[x][y])
                    + arr[x - 1][y]
                    + arr[x - 2][y]
                    + arr[x - 3][y];
            return XMAS.matcher(s).matches();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean checkTopLeftToBottomRight(char[][] arr, int x, int y) {
        try {
            final var s = String.valueOf(arr[x][y])
                    + arr[x + 1][y + 1]
                    + arr[x + 2][y + 2]
                    + arr[x + 3][y + 3];
            return XMAS.matcher(s).matches();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean checkBottomLeftToTopRight(char[][] arr, int x, int y) {
        try {
            final var s = String.valueOf(arr[x][y])
                    + arr[x + 1][y - 1]
                    + arr[x + 2][y - 2]
                    + arr[x + 3][y - 3];
            return XMAS.matcher(s).matches();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
