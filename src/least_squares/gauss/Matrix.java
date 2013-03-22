package least_squares.gauss;

/**
 * Created with IntelliJ IDEA.
 * User: Mark
 * Date: 22.03.13
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
public class Matrix {
    private double[][] A;

    public Matrix(double[][] Y) {
        if (Y.length != Y[0].length) {
            throw new IllegalArgumentException("Only NxN matrix supported");
        } else {
            A = new double[Y.length][Y[0].length];
        }
        for (int i = 0; i < A.length; i++) {
            System.arraycopy(Y[i], 0, A[i], 0, A[0].length);
        }
    }
    /*Возвращает матрицу в виде массива*/
    public double[][] toArray() {
        return A;
    }
    /*Возвращает указанный элемент матрицы*/
    public double getElement(int i, int j) {
        return A[i][j];
    }
    /*Умножает матрицу на m*/

    public Matrix mpl(double m) {
        double[][] C = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                C[i][j] = m * A[i][j];
            }
        }
        return (new Matrix(C));
    }
    /*Умножает строку с заданным номером на m*/

    public Matrix rowMpl(double rowNumber, double m) {
        double[][] C = new double[A.length][A[0].length];
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                if (i != rowNumber) {
                    C[i][j] = A[i][j];
                } else {
                    C[i][j] = m * A[i][j];
                }
            }
        }
        return new Matrix(C);
    }
    /*Меняет местами 2 строки*/
    public Matrix rowExchange(int row1, int row2) {
        double[][] C = new double[A.length][A[0].length];
        double[] temp = getRow(row2);
        for (int i = 0; i < A.length; i++) {
            System.arraycopy(A[i], 0, C[i], 0, A[0].length);
        }
        C[row2] = C[row1];
        C[row1] = temp;
        return new Matrix(C);
    }
    /*Меняет местами 2 столбца*/

    public Matrix colExchange(int col1, int col2) {
        double[][] C = new double[A.length][A[0].length];
        double[] temp = getCol(col2);
        for (int i = 0; i < A.length; i++) {
            System.arraycopy(A[i], 0, C[i], 0, A.length);
        }
        for (int j = 0; j < A.length; j++) {
            C[j][col2] = C[j][col1];
            C[j][col1] = temp[j];
        }
        return new Matrix(C);
    }
    /*К строке row2 прибавляеться строка row1 умноженная на c
     * и записываеться во вторую строку
     *     /*row1*с+row2 -> row2*/
    public Matrix strPlus(int row1, int row2, double c) {

        double[][] C = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (i != row2) {
                    C[i][j] = A[i][j];
                } else {
                    C[i][j] = c * A[row1][j] + A[i][j];
                }
            }
        }
        return new Matrix(C);
    }
    /* Возвращает строку с заданным номером*/

    public double[] getRow(int number) {
        double[] row = new double[A.length];
        System.arraycopy(A[number], 0, row, 0, A.length);
        return row;
    }
    /*Вовзращает столбец с заданым номером*/

    public double[] getCol(int number) {
        double[] col = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            col[i] = A[i][number];
        }
        return col;
    }

    /*Нормы матрицы*/
    //Евклидова
    public double norme() {
        double norm = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                norm = norm + A[i][j] * A[i][j];
            }
        }
        return Math.sqrt(norm);
    }
    //По столбцам

    public double norm1() {
        double[] norm = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            norm[i] = 0;
            for (int j = 0; j < A.length; j++) {
                norm[i] = norm[i] + Math.abs(A[j][i]);
            }
        }
        double max = norm[0];
        for (int i = 0; i < A.length; i++) {
            if (norm[i] > max) {
                max = norm[i];
            }
        }
        return max;
    }
    //По строкам
    public double normi() {
        double[] norm = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            norm[i] = 0;
            for (int j = 0; j < A.length; j++) {
                norm[i] = norm[i] + Math.abs(A[i][j]);
            }
        }
        double max = norm[0];
        for (int i = 0; i < A.length; i++) {
            if (norm[i] > max) {
                max = norm[i];
            }
        }
        return max;
    }
    /* STATIC METHODS */
    /*Умножает матрицы*/

    public static Matrix mpl(Matrix A, Matrix B) {
        if (A.toArray().length != B.toArray().length) {
            throw new IllegalArgumentException("Wrong Matrix Size ");
        } else {
            double[][] C = new double[A.toArray().length][A.toArray().length];
            for (int i = 0; i < A.toArray().length; i++) {
                for (int j = 0; j < A.toArray().length; j++) {
                    C[i][j] = 0;
                    for (int k = 0; k < A.toArray().length; k++) {
                        C[i][j] = C[i][j] + (A.getElement(i, k) * B.getElement(k, j));
                    }
                }
            }
            return new Matrix(C);
        }
    }
    /*Вычитает из матрицы А матрицу B*/

    public static Matrix minus(Matrix A, Matrix B) {
        if (A.toArray().length != B.toArray().length) {
            throw new IllegalArgumentException("Wrong Matrix Size ");
        } else {
            double[][] C = new double[A.toArray().length][A.toArray().length];
            for (int i = 0; i < A.toArray().length; i++) {
                for (int j = 0; j < A.toArray().length; j++) {
                    C[i][j] = A.getElement(i, j) - B.getElement(i, j);
                }
            }
            return new Matrix(C);
        }
    }
    /*Складывает матрицы*/

    public static Matrix plus(Matrix A, Matrix B) {
        if (A.toArray().length != B.toArray().length) {
            throw new IllegalArgumentException("Wrong Matrix Size ");
        } else {
            double[][] C = new double[A.toArray().length][A.toArray().length];
            for (int i = 0; i < A.toArray().length; i++) {
                for (int j = 0; j < A.toArray().length; j++) {
                    C[i][j] = A.getElement(i, j) + B.getElement(i, j);
                }
            }
            return new Matrix(C);
        }
    }

    public static void printArray(double X[][]) {
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[0].length; j++) {
                System.out.print("" + X[i][j] + "     ");
            }
            System.out.println("");
        }
    }

    public static void printArray(double[] X) {
        for (int i = 0; i < X.length; i++) {
            System.out.println(X[i]);
        }
    }

    public static void printMatrix(Matrix t) {
        printArray(t.toArray());
    }
    /*Удаляет строку и столбец с заданным номером*/
    public static double[][] delRowCol(double[][] mtx, double rowIndex, double colIndex) {
        double[][] res = new double[mtx.length - 1][mtx[0].length - 1];
        int t = 0, q = 0;
        o:
        for (int i = 0; i < mtx.length; i++) {
            if (i == rowIndex) {
                continue o;
            }
            y:
            for (int j = 0; j < mtx[0].length; j++) {
                if (j == colIndex) {
                    continue y;
                }
                res[t][q] = mtx[i][j];
                q++;
            }
            q = 0;
            t++;
        }
        return res;
    }
    /* Заменяет столбец с номером changeIndex на newCol*/
    public static double[][] changeCol(double[][] mtx, double[] newCol, double changeIndex) {
        double[][] res = new double[mtx.length][mtx[0].length];
        for (int i = 0; i < mtx.length; i++) {
            for (int j = 0; j < mtx[0].length; j++) {
                if (j == changeIndex) {
                    res[i][j] = newCol[i];
                } else {
                    res[i][j] = mtx[i][j];
                }
            }
        }
        return res;
    }

}

