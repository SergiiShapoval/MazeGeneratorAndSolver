import maze.Main;
import org.hyperskill.hstest.v4.stage.MainMethodTest;
import org.hyperskill.hstest.v4.testcase.CheckResult;
import org.hyperskill.hstest.v4.testcase.TestCase;

import java.util.ArrayList;
import java.util.List;


class Clue {
    int size;
    boolean withPath;
    Clue(int s, boolean wp) {
        size = s;
        withPath = wp;
    }
}

public class MazeRunnerTest extends MainMethodTest<Clue> {

    public MazeRunnerTest() throws Exception {
        super(Main.class);
    }

    @Override
    public List<TestCase<Clue>> generateTestCases() {
        return List.of(
            new TestCase<Clue>()
                .setInput("1\n16\n0")
                .setAttach(new Clue(16, false)),

            new TestCase<Clue>()
                .setInput("1\n29\n3\ntest_maze.txt\n0")
                .setAttach(new Clue(29, false)),

            new TestCase<Clue>()
                .setInput("2\ntest_maze.txt\n4\n0")
                .setAttach(new Clue(29, false)),

            new TestCase<Clue>()
                .setInput("1\n32\n3\ntest_maze.txt\n0")
                .setAttach(new Clue(32, false)),

            new TestCase<Clue>()
                .setInput("2\ntest_maze.txt\n4\n0")
                .setAttach(new Clue(32, false)),

            new TestCase<Clue>()
                .setInput("2\ntest_maze.txt\n5\n0")
                .setAttach(new Clue(32, true))
        );
    }

    private List<String> getMaze(String reply, boolean withEscape) {

        List<String> maze = new ArrayList<>();
        String[] rows = reply.split("\n");

        boolean foundMaze = false;

        for (String row : rows) {
            boolean possibleMazeRow = true;
            boolean haveSpecialSymbol = false;
            for (char c : row.toCharArray()) {
                if (c == '█') {
                    haveSpecialSymbol = true;
                }
                if (!withEscape) {
                    if (c != '█' && c != ' ') {
                        possibleMazeRow = false;
                        break;
                    }
                } else {
                    if (c != '█' && c != ' ' && c != '/') {
                        possibleMazeRow = false;
                        break;
                    }
                }
            }
            if (haveSpecialSymbol && possibleMazeRow) {
                maze.add(row);
                foundMaze = true;
            } else if (foundMaze) {
                break;
            }
        }

        return maze;
    }

    @Override
    public CheckResult check(String reply, Clue clue) {

        List<String> maze = getMaze(reply, clue.withPath);

        if (maze.size() == 0) {
            return CheckResult.FALSE(
                "In this test some mazes should be shown, " +
                    "but we detected no mazes in the output"
            );
        }

        if (maze.size() != clue.size) {
            return new CheckResult(false,
                "Number of rows in the maze is incorrect");
        }

        int columnsLength = maze.get(0).length();

        if (columnsLength / 2 != clue.size) {
            return new CheckResult(false,
                "Number of columns in the maze is incorrect");
        }

        for (String row : maze) {
            int columnLength = row.length();
            if (columnLength != columnsLength) {
                return new CheckResult(false,
                    "Number of columns " +
                        "should be the same on every row");
            }
        }

        boolean haveEscape = false;

        for (String row : maze) {
            haveEscape = haveEscape || row.contains("/");
            if (haveEscape && clue.withPath) {
                return CheckResult.TRUE;
            }
        }

        if (clue.withPath) {
            return new CheckResult(false,
                "There is no escape path " +
                    "in maze after choosing option 5");
        }

        return CheckResult.TRUE;
    }
}
