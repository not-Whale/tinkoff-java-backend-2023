package edu.project2;

import edu.project2.generators.BinaryTreeGenerator;
import edu.project2.generators.DepthFirstSearchGenerator;
import edu.project2.generators.KruskalGenerator;
import edu.project2.generators.PrimGenerator;
import edu.project2.generators.SidewinderGenerator;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.render.SimpleRenderer;
import edu.project2.solvers.DepthFirstSearchRecSolver;
import edu.project2.solvers.DepthFirstSearchSolver;
import edu.project2.solvers.RandomMouseSolver;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Main() {}

    private static final BinaryTreeGenerator BINARY_TREE_GENERATOR = new BinaryTreeGenerator();

    private static final SidewinderGenerator SIDEWINDER_GENERATOR = new SidewinderGenerator();

    private static final DepthFirstSearchGenerator DFS_GENERATOR = new DepthFirstSearchGenerator();

    private static final KruskalGenerator KRUSKAL_GENERATOR = new KruskalGenerator();

    private static final PrimGenerator PRIM_GENERATOR = new PrimGenerator();

    private static final SimpleRenderer SIMPLE_RENDERER = new SimpleRenderer();

    private static final RandomMouseSolver RANDOM_MOUSE_SOLVER = new RandomMouseSolver();

    private static final DepthFirstSearchSolver DFS_SOLVER = new DepthFirstSearchSolver();

    private static final DepthFirstSearchRecSolver DFS_REC_SOLVER = new DepthFirstSearchRecSolver();

    private static final String WITHOUT_HIGHLIGHT = "\033[0m";

    private static final String RED_SYMBOLS = "\033[031m";

    private static final String INCORRECT_COORD_MESSAGE =
        RED_SYMBOLS + "Координаты точки: два целых положительных числа!\n" + WITHOUT_HIGHLIGHT;

    private static final String INCORRECT_GENERATOR_NUMBER_MESSAGE =
        RED_SYMBOLS + "Номер алгоритма: целое число от 1 до 5!\n" + WITHOUT_HIGHLIGHT;

    private static final String INCORRECT_SOLVER_NUMBER_MESSAGE =
        RED_SYMBOLS + "Номер алгоритма: целое число от 1 до 3!\n" + WITHOUT_HIGHLIGHT;

    private static final String INCORRECT_MAZE_SIZE_MESSAGE =
        RED_SYMBOLS + "Измерения лабиринта: два целых положительных числа!\n" + WITHOUT_HIGHLIGHT;

    private static final String SOMETHING_WRONG_MESSAGE = "Упс! Что-то пошло не так...";

    private static final int MIN_ALGORITHM_NUMBER = 1;

    private static final int MAX_GENERATOR_NUMBER = 5;

    private static final int MAX_SOLVER_NUMBER = 3;

    private static final int BINARY_TREE_GENERATOR_NUMBER = 1;

    private static final int SIDEWINDER_GENERATOR_NUMBER = 2;

    private static final int DFS_GENERATOR_NUMBER = 3;

    private static final int KRUSKAL_GENERATOR_NUMBER = 4;

    private static final int PRIM_GENERATOR_NUMBER = 5;

    private static final int RANDOM_MOUSE_SOLVER_NUMBER = 1;

    private static final int DFS_SOLVER_NUMBER = 2;

    private static final int DFS_REC_SOLVER_NUMBER = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            Maze maze = readMaze(scanner);
            print("Построенный лабиринт:\n" + SIMPLE_RENDERER.render(maze));

            List<Coordinate> path = readSolution(scanner, maze);
            print("Построенный путь:\n" + SIMPLE_RENDERER.render(maze, path));

            exit = checkRerun(scanner);
        }
    }

    private static Maze readMaze(Scanner scanner) {
        int generatorNumber = readGeneratorNumber(scanner, "\n");
        while (generatorNumber < MIN_ALGORITHM_NUMBER || generatorNumber > MAX_GENERATOR_NUMBER) {
            generatorNumber = readGeneratorNumber(
                scanner,
                INCORRECT_GENERATOR_NUMBER_MESSAGE
            );
        }

        scanner.nextLine();
        int[] size = readMazeSize(scanner, "");
        while (size == null) {
            size = readMazeSize(
                scanner,
                INCORRECT_MAZE_SIZE_MESSAGE
            );
        }

        int mazeHeight = size[0];
        int mazeWidth = size[1];
        return switch (generatorNumber) {
            case BINARY_TREE_GENERATOR_NUMBER -> BINARY_TREE_GENERATOR.generate(mazeHeight, mazeWidth);
            case SIDEWINDER_GENERATOR_NUMBER -> SIDEWINDER_GENERATOR.generate(mazeHeight, mazeWidth);
            case DFS_GENERATOR_NUMBER -> DFS_GENERATOR.generate(mazeHeight, mazeWidth);
            case KRUSKAL_GENERATOR_NUMBER -> KRUSKAL_GENERATOR.generate(mazeHeight, mazeWidth);
            case PRIM_GENERATOR_NUMBER -> PRIM_GENERATOR.generate(mazeHeight, mazeWidth);
            default -> throw new RuntimeException(SOMETHING_WRONG_MESSAGE);
        };
    }

    private static List<Coordinate> readSolution(Scanner scanner, Maze maze) {
        int solverNumber = readSolverNumber(scanner, "\n");
        while (solverNumber < MIN_ALGORITHM_NUMBER || solverNumber > MAX_SOLVER_NUMBER) {
            solverNumber = readSolverNumber(
                scanner,
                INCORRECT_SOLVER_NUMBER_MESSAGE
            );
        }

        scanner.nextLine();
        int[] startCell = readStartCoord(scanner, "");
        while (startCell == null) {
            startCell = readStartCoord(
                scanner,
                INCORRECT_COORD_MESSAGE
            );
        }

        int[] endCell = readEndCoord(scanner, "");
        while (endCell == null) {
            endCell = readEndCoord(
                scanner,
                INCORRECT_COORD_MESSAGE
            );
        }

        Coordinate startCoord = new Coordinate(startCell[0], startCell[1]);
        Coordinate endCoord = new Coordinate(endCell[0], endCell[1]);
        return switch (solverNumber) {
            case RANDOM_MOUSE_SOLVER_NUMBER -> RANDOM_MOUSE_SOLVER.solve(maze, startCoord, endCoord);
            case DFS_SOLVER_NUMBER -> DFS_SOLVER.solve(maze, startCoord, endCoord);
            case DFS_REC_SOLVER_NUMBER -> DFS_REC_SOLVER.solve(maze, startCoord, endCoord);
            default -> throw new RuntimeException(SOMETHING_WRONG_MESSAGE);
        };
    }

    private static boolean checkRerun(Scanner scanner) {
        boolean correct = false;
        boolean exit = false;
        while (!correct) {
            String anotherTime = readAnotherTimeAnswer(scanner);
            switch (anotherTime.strip().toLowerCase()) {
                case "yes" -> {
                    // exit = false;
                    correct = true;
                }
                case "no" -> {
                    exit = true;
                    correct = true;
                }
                default -> { }
            }
        }
        return exit;
    }

    private static int[] readEndCoord(Scanner scanner, String message) {
        return readCoord(scanner, message, "конечной");
    }

    private static int[] readStartCoord(Scanner scanner, String message) {
        return readCoord(scanner, message, "начальной");
    }

    private static int[] readCoord(Scanner scanner, String message, String coordType) {
        print(message + "Введите координаты " + coordType + " точки через пробел:");
        String coordString = scanner.nextLine();
        String[] coordStringArray = coordString.strip().split(" ");
        if (coordStringArray.length != 2) {
            return null;
        }
        return new int[] {
            Integer.parseInt(coordStringArray[0]),
            Integer.parseInt(coordStringArray[1])
        };
    }

    private static int[] readMazeSize(Scanner scanner, String message) {
        print(message + "Введите высоту и длину лабиринта через пробел:");
        String sizeString = scanner.nextLine();
        String[] sizeStringArray = sizeString.strip().split(" ");
        if (sizeStringArray.length != 2) {
            return null;
        }
        return new int[] {
            Integer.parseInt(sizeStringArray[0]),
            Integer.parseInt(sizeStringArray[1])
        };
    }

    private static String readAnotherTimeAnswer(Scanner scanner) {
        print("Хотите построить и решить новый лабиринт? (yes/no)");
        return scanner.nextLine().strip().toLowerCase();
    }

    private static int readSolverNumber(Scanner scanner, String message) {
        print(message + """
                Выберите алгоритм для поиска пути в лабиринте:
                1. Случайный выбор пути (random mouse algorithm)
                2. Поиск в глубину
                3. Рекурсивный поиск в глубину""");
        return scanner.nextInt();
    }

    private static int readGeneratorNumber(Scanner scanner, String message) {
        print(message + """
                Выберите алгоритм для генерации лабиринта:
                1. Генерация с помощью построения бинарного дерева
                2. Генерация методом Sidewinder
                3. Генерация с помощью обхода в глубину
                4. Генерация с помощью алгоритма Крускаля
                5. Генерация с помощью алгоритма Прима""");
        return scanner.nextInt();
    }

    private static void print(String message) {
        System.out.println(message);
    }
}
