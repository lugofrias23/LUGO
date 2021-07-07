/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.JOptionPane;

/**
 *
 * @author LugoFrias
 */
public class Operaciones {

    /**
     * El metodo sumar, suma dos matrices devolviendo el resuktado
     *
     * @param matrizSumandoA
     * @param matrizSumandoB
     * @param nFilas
     * @param nColumnas
     * @return devuelve la matriz resultado de la suma
     */
    public float[][] sumarMatrices(float matrizSumandoA[][], float matrizSumandoB[][], int nFilas,
            final int nColumnas) {
        float[][] matrizResultado = new float[nFilas][nColumnas];

        for (int iFilas = 0; iFilas < nFilas; iFilas++) {
            for (int iColumnas = 0; iColumnas < nColumnas; iColumnas++) {
                matrizResultado[iFilas][iColumnas] = matrizSumandoA[iFilas][iColumnas]
                        + matrizSumandoB[iFilas][iColumnas];
            }
        }
        return matrizResultado;

    }
    
    
     public float[][] restarMatrices(float matrizRestandoA[][], float matrizRestandoB[][], int nFilas,
            final int nColumnas) {
        float[][] matrizResultado = new float[nFilas][nColumnas];

        for (int iFilas = 0; iFilas < nFilas; iFilas++) {
            for (int iColumnas = 0; iColumnas < nColumnas; iColumnas++) {
                matrizResultado[iFilas][iColumnas] = matrizRestandoA[iFilas][iColumnas]
                        - matrizRestandoB[iFilas][iColumnas];
            }
        }
        return matrizResultado;

    }
     
     public float[][] dividirMatrices(float matrizDividiendoA[][], float matrizDividiendoB[][], int nFilas,
            final int nColumnas) {
        float[][] matrizResultado = new float[nFilas][nColumnas];

        for (int iFilas = 0; iFilas < nFilas; iFilas++) {
            for (int iColumnas = 0; iColumnas < nColumnas; iColumnas++) {
                matrizResultado[iFilas][iColumnas] = matrizDividiendoA[iFilas][iColumnas]
                        / matrizDividiendoB[iFilas][iColumnas];
            }
        }
        return matrizResultado;

    }

    /**
     * El metodo multEscalar multiplica una matriz por un numero especifico
     *
     * @param escalar
     * @param matriz
     * @param nFilas
     * @param nColumnas
     * @return devuelve la matriz resultante de la multiplicacion
     */
    public float[][] multMatrizPorEscalar(float escalar, float matriz[][], final int nFilas,
            final int nColumnas) {
        float[][] matrizEscalar = new float[nFilas][nColumnas];

        for (int iFilas = 0; iFilas < nFilas; iFilas++) {
            for (int iColumnas = 0; iColumnas < nColumnas; iColumnas++) {
                matrizEscalar[iFilas][iColumnas] = escalar * matriz[iFilas][iColumnas];
            }
        }
        return matrizEscalar;
    }

    /**
     * el metodo podructo multiplica dos matrices de los cuales deben de tener
     * el mismo numero de filas y columnas
     *
     * @param matrizFactorA
     * @param matrizFactorB
     * @param nFilasA
     * @param nColumnasA
     * @param nColumnasB
     * @return regresa la matruz rsultante del producto
     */
    public float[][] productoDosMatrices(float matrizFactorA[][], float matrizFactorB[][], int nFilasA,
            final int nColumnasA, final int nColumnasB) {
        float[][] matrizResultado = new float[nFilasA][nColumnasB];

        for (int iFilas = 0; iFilas < nFilasA; iFilas++) {
            for (int iColumnas = 0; iColumnas < nColumnasB; iColumnas++) {
                for (int k = 0; k < nColumnasA; k++) {
                    matrizResultado[iFilas][iColumnas] = (matrizFactorA[iFilas][k] * matrizFactorB[k][iColumnas])
                            + matrizResultado[iFilas][iColumnas];
                }
            }
        }

        return matrizResultado;
    }

    /**
     * El metodo determinante obtiene la determinante de una matriz
     *
     * @param matriz
     * @return devuelve el determinante resultante
     */
    public float determinante(float[][] matriz) {
        float determinante;
        if (matriz.length == 2) {
            determinante = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
            return determinante;
        }
        float suma = 0;
        for (int contadorFila = 0; contadorFila < matriz.length; contadorFila++) {
            float[][] matrizTemporal = new float[matriz.length - 1][matriz.length - 1];
            for (int contadorColumna = 0; contadorColumna < matriz.length; contadorColumna++) {
                if (contadorColumna != contadorFila) {
                    for (int k = 1; k < matriz.length; k++) {
                        int indice = -1;
                        if (contadorColumna < contadorFila) {
                            indice = contadorColumna;
                        } else if (contadorColumna > contadorFila) {
                            indice = contadorColumna - 1;
                        }
                        matrizTemporal[indice][k - 1] = matriz[contadorColumna][k];
                    }
                }
            }
            if (contadorFila % 2 == 0) {
                suma += matriz[contadorFila][0] * determinante(matrizTemporal);
            } else {
                suma -= matriz[contadorFila][0] * determinante(matrizTemporal);
            }
        }
        return suma;
    }

    /**
     * Metodo que obtiene la inversa de una matriz
     *
     * @param matriz
     * @return
     */
    public static float[][] inversaPorGaussJordan(float[][] matriz) {
        int dimension = matriz.length;
        //se crea la matriz identidad correspondiente
        float[][] matrizIdentidad = new float[dimension][dimension];

        for (int iFila = 0; iFila < dimension; iFila++) {
            for (int iColumna = 0; iColumna < dimension; iColumna++) {
                if (iFila == iColumna) {
                    matrizIdentidad[iFila][iColumna] = 1;
                } else {
                    matrizIdentidad[iFila][iColumna] = 0;
                }
            }
        }

        float[][] matrizAumentada = solucionConGauss(matriz, matrizIdentidad);
        float[][] matrizInversa = new float[dimension][dimension];

        for (int contadorFila = 0; contadorFila < dimension; contadorFila++) {
            for (int contadorColumna = 0; contadorColumna < dimension; contadorColumna++) {
                matrizInversa[contadorFila][contadorColumna]
                        = matrizAumentada[contadorFila][contadorColumna + dimension];
            }
        }
        return matrizInversa;
    }

    /**
     * soluciona una ecuacion por Gauss.
     *
     * @param matrizEcuaciones matriz de entrada
     * @param ResultadosEcuaciones
     * @return
     */
    public static float[][] solucionConGauss(float matrizEcuaciones[][], float ResultadosEcuaciones[][]) {
        float[][] matrizAumentada;
        matrizAumentada = AumentaMatrizEcuacion(matrizEcuaciones, ResultadosEcuaciones);

        for (int contFila = 0; contFila < matrizAumentada.length; contFila++) {
            ChecarPivotes(matrizAumentada, contFila);
            dividirEntrePivote(matrizAumentada, contFila);
            escalonaFila(matrizAumentada, contFila);
        }
        return matrizAumentada;
    }

    /**
     * Metodo que une la matriz de ecuaciones con la matriz de resultados para
     * hacer una sola.
     *
     * @param matriz
     * @param matrizVector
     */
    private static float[][] AumentaMatrizEcuacion(float matriz[][], float matrizVector[][]) {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        float[][] matrizAumentada = new float[filas][columnas + matrizVector[0].length];
        for (int indFilas = 0; indFilas < matrizAumentada.length; indFilas++) {
            for (int indColum = 0; indColum < matrizAumentada[0].length; indColum++) {
                if (indColum < columnas) {
                    matrizAumentada[indFilas][indColum] = matriz[indFilas][indColum];
                } else {
                    matrizAumentada[indFilas][indColum] = matrizVector[indFilas][indColum - columnas];
                }
            }
        }
        return matrizAumentada;
    }

    /**
     * Metodo que revisa si hay un pivote que pueda ser cero, si no es cero,
     * continua haciendo el algoritmo, si es cero, llama a un metodo de busqueda
     * de una fila con un pivote que no sea cero y luego intercambia las filas
     * para que no se produzca error de division entre cero.
     *
     * @param matriz a la que se le revisaran los pivotes en las filas
     * @param indPivote es indice del pivote en el que preguntara si es distinto
     * de cero
     */
    private static void ChecarPivotes(float matriz[][], int indPivote) {
        if (matriz[indPivote][indPivote] != 0.0) {
        } else {
            //continua con el proceso 
            int indicePivoteEncontrado = buscarFilaSinPivoteCero(matriz, indPivote + 1);
            intercambiarFilas(matriz, indPivote, indicePivoteEncontrado);
        }
    }

    /*
     *intercambia 2 filas de una matriz
     */
    private static void intercambiarFilas(float matriz[][], int filaA, int filaB) {
        int colum = matriz[0].length;
        for (int columnasCount = 0; columnasCount < colum; columnasCount++) {
            float aux = matriz[filaA][columnasCount];
            matriz[filaA][columnasCount] = matriz[filaB][columnasCount];
            matriz[filaB][columnasCount] = aux;
        }
    }

    /**
     * Metodo que busca una fila de la matriz en el que el pivote sea distinto
     * de cero
     *
     * @param matriz matriz a la cual se le busca el pivote
     * @param indiceABuscar indice que se empezara a buscar un pivote valido
     * @return el indice de la fila de la matriz con pivote valido, distinto de
     * cero
     */
    private static int buscarFilaSinPivoteCero(float matriz[][], int indiceABuscar) {
        int filas = matriz.length;
        for (int contadorFila = indiceABuscar; contadorFila < filas; contadorFila++) {
            if (matriz[contadorFila][contadorFila] != 0.0) {
                System.out.println("ENCONTRADO" + contadorFila);
                return contadorFila;
            }
        }
        JOptionPane.showMessageDialog(null, "ERROR");
        return 0;
    }

    /**
     * Metodo que divide una fila de una matriz entre el pivote para que la
     * casilla del pivote se vuelva 1.
     *
     * @param matriz matriz a la que se le aplicara el algoritmo
     * @param indicePivote indice del pivote que se esta analizando
     */
    private static void dividirEntrePivote(float matriz[][], int indicePivote) {
        int columnas = matriz[0].length;
        float pivote = matriz[indicePivote][indicePivote];
        for (int contadorColum = 0; contadorColum < columnas; contadorColum++) {
            matriz[indicePivote][contadorColum] = matriz[indicePivote][contadorColum] / pivote;
        }
    }

    /**
     * Metodo que escalona la fila del pivote especifico, volviendo 0 toda la
     * columna del pivote, a excepcion del pivote mismo, de acuerdo al agoritmo
     * de Gauss Jordan.
     *
     * @param matriz matriz a la que se le escalonara la fila
     * @param indicePivote indice del pivote que se esta analizando
     */
    private static void escalonaFila(float matriz[][], int indicePivote) {
        int filas = matriz.length;
        for (int contadorFila = 0; contadorFila < filas; contadorFila++) {
            if (contadorFila != indicePivote) {
                multiplicarCoeficienteOpuesto(contadorFila, indicePivote, matriz);
            }
        }
    }

    /**
     * Metodo que multiplica la fila de la matriz donde esta el pivote, por el
     * opuesto en signo del numero de la misma columna donde esta el pivote, y
     * luego lo suma con la otra fila para poder hacer que la columna del pivote
     * especifico, se encuentre entre 0's.
     *
     * @param matriz matriz a la que se le aplicara el algoritmo
     * @param indicePivote indice del pivote que se esta analizando
     */
    private static void multiplicarCoeficienteOpuesto(int contadorFila, int indicePivote, float[][] matriz) {
        int columnas = matriz[0].length;
        float coeficienteOpuesto = -1 * matriz[contadorFila][indicePivote];
        for (int contadorColum = 0; contadorColum < columnas; contadorColum++) {
            matriz[contadorFila][contadorColum] = (coeficienteOpuesto * matriz[indicePivote][contadorColum])
                    + matriz[contadorFila][contadorColum];
        }
    }

    /**
     * @param matrizEcuaciones matriz de ecuaciones a la que se le aplicara el
     * algoritmo
     * @param vectorResultados matriz con los resultados de las ecuaciones
     * @return matrizResultado
     */
    public float[][] solucionCramer(float[][] matrizEcuaciones, float[][] vectorResultados) {

        float matrizResultado[][] = new float[matrizEcuaciones.length][matrizEcuaciones.length];
        float determinanteMatrizEcuaciones = determinante(matrizEcuaciones);

        float determinanteMatrizAuxiliar;
        float matrizAuxiliar[][] = new float[matrizEcuaciones.length][matrizEcuaciones.length];

        for (int i = 0; i < (matrizEcuaciones.length); i++) {
            matrizAuxiliar = sustituirColumna(matrizEcuaciones, vectorResultados, i);
            determinanteMatrizAuxiliar = determinante(matrizAuxiliar);
            matrizResultado[i][0] = determinanteMatrizAuxiliar / determinanteMatrizEcuaciones;
        }

        return matrizResultado;
    }
    /*
     *Metodo que suustituye una columna de una matriz con una columna dada en la posicion
     *especifica
     * @param matriz 
     * @param 
     * @param
     */

    private float[][] sustituirColumna(float matriz[][], float Columna[][], int posicion) {
        float nuevaMatriz[][] = new float[matriz.length][matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (j == posicion) {
                    nuevaMatriz[i][j] = Columna[i][0];
                } else {
                    nuevaMatriz[i][j] = matriz[i][j];
                }
            }
        }
        return nuevaMatriz;
    }

    
}
