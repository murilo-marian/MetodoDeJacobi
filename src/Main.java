import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {

    static List<Double> anterior = new ArrayList<>();
    static List<Double> atual = new ArrayList<>();
    static double precisao; //tolerancia de erro
    static int size = 3; //grau da matriz

    public static void main(String[] args) {
        double[][] matriz = {{-3, 2, -1},{1/2, 6, 2},{-4, 2, -5}};
        double[] y = {2, 1, 6};
        precisao = 0.003;
        if (converge(matriz)) {
            jacobi(matriz, y);
            System.out.println(atual);
        }
    }

    public static void jacobi(double[][] matriz, double[] y) {
        //tem que checar aqui se a diferença é menor que o erro
        if (!anterior.isEmpty() && !atual.isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (Math.abs(atual.get(i) - anterior.get(i)) < precisao) {
                    if (i == size - 1) {
                        return; //todos os valores abaixo da taxa de erro
                    }
                } else {
                    break;
                }
            }
        }

        Collections.copy(anterior, atual);

        if (anterior.isEmpty()) {
            for (int i = 0; i < size; i++) {
                 anterior.add((double) 0);
            }
        } else {
            atual.clear();
            for (int i = 0; i < size; i++) {
                double teste = y[i];
                for (int j = 0; j < size; j++) { //itera pelos números de uma determinada linha
                    if (i != j) { //apenas se o número não for o que está sendo isolado
                        teste = teste - (matriz[i][j] * anterior.get(j));
                    }
                }
                teste = teste / matriz[i][i];
                atual.add(teste);
            }
        }
        jacobi(matriz, y); //recursividade foda
    }

    public static boolean converge(double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            double soma = 0;
            for (int j = 0; j < matriz.length; j++) {
                if (i != j) {
                    soma += Math.abs(matriz[i][j]);
                }
            }
            if (soma / matriz[i][i] > 1) {
                return false;
            }
        }
        return true;
    }
}
