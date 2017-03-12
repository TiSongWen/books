package chapter2;

/**
 * Created by tisong on 3/12/17.
 */
public class FindInPartiallySortedMatrix {


    public boolean findNumber(int[][] array, int rows, int colums, int number) {
        if (array == null || rows <= 0 || colums <= 0) {
            return false;
        }

        return findNumber(array, 0, colums-1, number, rows);
    }


    /**
     *
     * @param array
     * @param row 几行
     * @param column
     * @param number
     * @return
     */
    private boolean findNumber(int[][] array, int row, int column, int number, int rows) {
        if (row == rows || column < 0) {
            return false;
        }

        if (array[row][column] == number) {
            return true;
        }

        if (array[row][column] > number) {
            return findNumber(array, row, column-1, number, rows);
        }

        if (array[row][column] < number) {
            return findNumber(array, row+1, column, number, rows);
        }

        return false;

    }


    public static void main(String[] args) {

        int[][] array = {{1,2,8,9}, {2,4,8,12}, {4,7,10,13}, {6,8,11,15}};

        System.out.println(new FindInPartiallySortedMatrix().findNumber(array, 4, 4, 5));
    }
}
