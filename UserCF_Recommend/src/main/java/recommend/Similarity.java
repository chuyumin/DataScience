package recommend;

public class Similarity {
    private double[][] matrix_y;
    private double[][] matrix_x1;
    private double[][] matrix_x2;

    public Similarity(double[][] matrix_y, double[][] matrix_x1, double[][] matrix_x2) {
        this.matrix_y = matrix_y;
        this.matrix_x1 = matrix_x1;
        this.matrix_x2 = matrix_x2;
    }

    public double[][] getMatrix_y() {
        return matrix_y;
    }

    public void setMatrix_y(double[][] matrix_y) {
        this.matrix_y = matrix_y;
    }

    public double[][] getMatrix_x1() {
        return matrix_x1;
    }

    public void setMatrix_x1(double[][] matrix_x1) {
        this.matrix_x1 = matrix_x1;
    }

    public double[][] getMatrix_x2() {
        return matrix_x2;
    }

    public void setMatrix_x2(double[][] matrix_x2) {
        this.matrix_x2 = matrix_x2;
    }
}
