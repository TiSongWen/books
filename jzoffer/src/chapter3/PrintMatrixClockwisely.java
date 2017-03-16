package chapter3;

/**
 * Created by tisong on 3/16/17.
 */
public class PrintMatrixClockwisely {

    private static int circle = 0;

    public static void printMatrixClockwisely(int[][] numbers, int rows, int columns) {
        if (numbers == null || rows < 1 || columns < 1) {
            return ;
        }

        int min = Math.min(rows, columns);
        circle = min / 2;

        printCircle(numbers, 0, rows-1, 0, columns-1, 0);
    }

    private static void printCircle(int[][] numbers, int startRow, int endRow, int startColumn, int endColumn, int circle) {
        if (circle > PrintMatrixClockwisely.circle) {
            return ;
        }

        for (int i = startRow; i <= endRow; i++) {
            System.out.print(numbers[startColumn][i] + " ");
        }

        for (int i = startColumn+1; i <= endColumn; i++) {
            System.out.print(numbers[i][endRow] + " ");
        }

        if (startColumn < endColumn) {
            for (int i = endRow-1; i > startRow; i--) {
                System.out.print(numbers[endColumn][i] + " ");
            }
        }

        if (startRow < endRow) {
            for (int i = endColumn; i > startColumn; i--) {
                System.out.print(numbers[i][startRow] + " ");
            }
        }

        System.out.println("");

        printCircle(numbers, startRow+1, endRow-1, startColumn+1, endColumn-1, ++circle);
    }

    public static void main(String[] args) {
        int rows = 3;
        int columns = 4;

        int[][] numbers = new int[rows][columns];

        int num = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                numbers[i][j] = num++;
            }
        }

        printMatrixClockwisely(numbers, columns, rows);
    }
}
