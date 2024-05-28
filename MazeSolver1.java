import java.util.*;

public class MazeSolver1 {

    static int rows, cols;
    static int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} }; // Right, Down, Left, Up

    public static boolean solveMazeUsingDFS(int startX, int startY, boolean[][] visited, List<int[]> path) {
        Stack<int[]> stack = new Stack<>();
        Stack<List<int[]>> pathStack = new Stack<>();

        stack.push(new int[] { startX, startY });
        pathStack.push(new ArrayList<>());

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            List<int[]> currentPath = pathStack.pop();

            int x = current[0];
            int y = current[1];

            if (x == rows - 1 && y == cols - 1) {
                currentPath.add(new int[] { x, y });
                path.addAll(currentPath);
                return true;
            }

            visited[x][y] = true;
            currentPath.add(new int[] { x, y });

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (isValidCell(newX, newY) && !visited[newX][newY]) {
                    stack.push(new int[] { newX, newY });
                    pathStack.push(new ArrayList<>(currentPath));
                }
            }
        }

        return false;
    }

    // Example of isValidCell function
    public static boolean isValidCell(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public static void main(String[] args) {
        rows = 5; // Example number of rows
        cols = 5; // Example number of columns
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> path = new ArrayList<>();

        if (solveMazeUsingDFS(0, 0, visited, path)) {
            System.out.println("Path found:");
            for (int[] cell : path) {
                System.out.println(Arrays.toString(cell));
            }
        } else {
            System.out.println("No path found.");
        }
    }
}