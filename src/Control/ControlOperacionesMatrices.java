/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Operaciones;
import Vista.VistaOperacionesMatrices;
import Modelo.TipoOperacion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author LugoFrias
 */

/**
 * Declaracion de la clase ControlOperacionesMatrices
 *
 *
 *
 */
public class ControlOperacionesMatrices implements ActionListener {

    //Definicion de los atributos privados
    private final VistaOperacionesMatrices view;
    private final Operaciones modelo;
    private TipoOperacion tipoOperacion;

    /**
     * Creacion del constructor
     *
     * @param viewOperaciones
     * @param operacionesMatriz
     */
    public ControlOperacionesMatrices(VistaOperacionesMatrices viewOperaciones, Operaciones operacionesMatriz) {
        this.view = viewOperaciones;
        this.modelo = operacionesMatriz;
        this.view.getjButtonDeterminante().addActionListener(this);
        this.view.getjButtonEstablecer().addActionListener(this);
        this.view.getjButtonInversa().addActionListener(this);
        this.view.getjButtonLimpiar().addActionListener(this);
        this.view.getjButtonMultipEscalar().addActionListener(this);
        this.view.getjButtonMultiplicación().addActionListener(this);
        this.view.getjButtonResultado().addActionListener(this);
        this.view.getjButtonSolucionCramer().addActionListener(this);
        this.view.getjButtonSolucionGauss().addActionListener(this);
        this.view.getjButtonSuma().addActionListener(this);
        this.view.getjButtonResta().addActionListener(this);
        this.view.getjButtonDivision().addActionListener(this);
        this.view.getjComboBoxColumnas().addActionListener(this);
        this.view.getjComboBoxFilas().addActionListener(this);
        this.view.getjButtonLimpiarMatriz().addActionListener(this);
    }

    /**
     * Metodo que utiliza el ActionListener para observar eventos de la vista
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //llamada al metodo que verifica la operacion seleccionada
        botonesOperaciones(e);

        if (view.getjButtonResultado() == e.getSource()) {
            TableCellEditor celltable = view.getjTableMatriz1().getCellEditor();  //Trae la celda que se esta editando
            TableCellEditor celltable2 = view.getjTableMatriz2().getCellEditor();
            if ((celltable != null) && (celltable2 != null)) {
                celltable.stopCellEditing(); //Detiene la edicion de la celda
                celltable2.stopCellEditing();
            }
            if ((celltable != null)) {
                celltable.stopCellEditing(); //Detiene la edicion de la celda 
            }
            if ((celltable2 != null)) {
                celltable2.stopCellEditing();
            }
            eventoOperaciones();
        }

        //evento del boton Limpiar
        if (view.getjButtonLimpiar() == e.getSource()) {
            habilitarOperaciones();
            regresarColorOperaciones();
            limpiarMatrices();
            view.getjComboBoxColumnas().setSelectedIndex(0);
            view.getjComboBoxFilas().setSelectedIndex(0);
            view.getjLabelOperador().setText(null);

        }
        //evento del boton Establecer
        if (view.getjButtonEstablecer() == e.getSource()) {
            int filas = Integer.parseInt(String.valueOf(view.getjComboBoxFilas().getSelectedItem()));
            int columnas = Integer.parseInt(String.valueOf(view.getjComboBoxColumnas().getSelectedItem()));
            view.getjButtonResultado().setEnabled(true);
            if (filas != columnas) {
                if (tipoOperacion == TipoOperacion.MULT_MATRIZ || tipoOperacion == TipoOperacion.DIVISION) {
                    JOptionPane.showMessageDialog(null, "las filas de la "
                            + "matriz A debe coicidir con las columnas de la matriz B");
                } else if (tipoOperacion == TipoOperacion.INVERSA) {
                    JOptionPane.showMessageDialog(null, "Solo las matrices de NXN tienen inversa");
                } else if (tipoOperacion == TipoOperacion.DETERMINANTE) {
                    JOptionPane.showMessageDialog(null, "Solo las matrices de NXN tienen determinante");
                } else if ((tipoOperacion == TipoOperacion.GAUSS) || (tipoOperacion == TipoOperacion.CRAMER)) {
                    JOptionPane.showMessageDialog(null, "Las matriz solo puede ser cuadrada");
                } else {
                    establecerMatriz(filas, columnas);
                }
            } else {
                establecerMatriz(filas, columnas);
            }

        }
        if (view.getjButtonLimpiarMatriz() == e.getSource()) {
            limpiarMatrices();
        }
        //evento al momento de seleccionar el tamaño de las filas y columnas
        if (view.getjComboBoxFilas() == e.getSource() || view.getjComboBoxColumnas() == e.getSource()) {
            if (view.getjComboBoxColumnas().getSelectedItem() != "--"
                    && view.getjComboBoxFilas().getSelectedItem() != "--") {
                view.getjButtonEstablecer().setEnabled(true);
            } else {
                view.getjButtonEstablecer().setEnabled(false);
            }
        }
    }

    /**
     * Metodo que construye las matrices despues de seleccionar el tamaño de
     * filas y columnas
     *
     * @param filas
     * @param columnas
     */
    public void establecerMatriz(int filas, int columnas) {
        DefaultTableModel modelMatriz1 = (DefaultTableModel) view.getjTableMatriz1().getModel();
        DefaultTableModel modelMatriz2 = (DefaultTableModel) view.getjTableMatriz2().getModel();
        modelMatriz1.setRowCount(filas);
        modelMatriz1.setColumnCount(columnas);
        //dependiendo de la operacion seleccionada se crea la segunda matriz
       if (tipoOperacion == TipoOperacion.MULT_ESCALAR || tipoOperacion == TipoOperacion.DIVISION) {
            //cuando es multiplicacion con un escalar
            modelMatriz2.setColumnCount(1);
            modelMatriz2.setRowCount(1);
        }
       if ((tipoOperacion == TipoOperacion.SUMA) || (tipoOperacion == TipoOperacion.RESTA)
               || (tipoOperacion == TipoOperacion.MULT_MATRIZ) || (tipoOperacion == TipoOperacion.DIVISION)) {
            //cuando es suma o multiplicacion de matrices
            modelMatriz2.setColumnCount(columnas);
            modelMatriz2.setRowCount(filas);
        }
        if ((tipoOperacion == TipoOperacion.GAUSS) || (tipoOperacion == TipoOperacion.CRAMER)) {
            modelMatriz2.setColumnCount(1);
            modelMatriz2.setRowCount(filas);
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                view.getjTableMatriz1().setValueAt(0, i, j);
            }
        }
        for (int i = 0; i < modelMatriz2.getRowCount(); i++) {
            for (int j = 0; j < modelMatriz2.getColumnCount(); j++) {
                view.getjTableMatriz2().setValueAt(0, i, j);
            }
        }
    }

    /**
     * Metodo que bloquea los botones de operaciones al seleccionar una de las
     * operaciones de matrices
     *
     */
    public void deshabilitarOperaciones() {
        view.getjButtonDeterminante().setEnabled(false);
        view.getjButtonInversa().setEnabled(false);
        view.getjButtonLimpiar().setEnabled(true);
        view.getjButtonMultipEscalar().setEnabled(false);
        view.getjButtonMultiplicación().setEnabled(false);
        view.getjButtonSolucionCramer().setEnabled(false);
        view.getjButtonSolucionGauss().setEnabled(false);
        view.getjButtonSuma().setEnabled(false);
        view.getjButtonResta().setEnabled(false);
        view.getjButtonDivision().setEnabled(false);
        view.getjComboBoxColumnas().setEnabled(true);
        view.getjComboBoxFilas().setEnabled(true);
        view.getjButtonLimpiarMatriz().setEnabled(true);
    }

    /**
     * Metodo que habilita los botones de las operaciones e inhabilita los
     * botones para realizar las operaciones
     */
    public void habilitarOperaciones() {
        view.getjButtonDeterminante().setEnabled(true);
        view.getjButtonInversa().setEnabled(true);
        view.getjButtonLimpiar().setEnabled(false);
        view.getjButtonMultipEscalar().setEnabled(true);
        view.getjButtonMultiplicación().setEnabled(true);
        view.getjButtonResultado().setEnabled(false);
        view.getjButtonSolucionCramer().setEnabled(true);
        view.getjButtonSolucionGauss().setEnabled(true);
        view.getjButtonSuma().setEnabled(true);
        view.getjButtonResta().setEnabled(true);
        view.getjButtonDivision().setEnabled(true);
        view.getjComboBoxColumnas().setEnabled(false);
        view.getjComboBoxFilas().setEnabled(false);
        view.getjButtonEstablecer().setEnabled(false);
        view.getjButtonLimpiarMatriz().setEnabled(false);
    }

    /**
     * Metodo que devuelve el color del boton seleccionado
     */
    @SuppressWarnings("empty-statement")
    public void regresarColorOperaciones() {
        view.getjButtonDeterminante().setBackground(Color.LIGHT_GRAY);
        view.getjButtonInversa().setBackground(Color.LIGHT_GRAY);
        view.getjButtonMultipEscalar().setBackground(Color.LIGHT_GRAY);;
        view.getjButtonMultiplicación().setBackground(Color.LIGHT_GRAY);;
        view.getjButtonSolucionCramer().setBackground(Color.LIGHT_GRAY);;
        view.getjButtonSolucionGauss().setBackground(Color.LIGHT_GRAY);;
        view.getjButtonSuma().setBackground(Color.LIGHT_GRAY);
        view.getjButtonResta().setBackground(Color.LIGHT_GRAY);
        view.getjButtonDivision().setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Metodo que limpia las matrices previamente construidas al oprimir el
     * boton Establecer Matriz
     */
    public void limpiarMatrices() {
        DefaultTableModel modelMatriz1 = (DefaultTableModel) view.getjTableMatriz1().getModel();
        DefaultTableModel modelMatriz2 = (DefaultTableModel) view.getjTableMatriz2().getModel();
        DefaultTableModel modelMatrizResult = (DefaultTableModel) view.getjTableResultados().getModel();
        modelMatriz1.setRowCount(0);
        modelMatriz1.setColumnCount(0);
        modelMatriz2.setRowCount(0);
        modelMatriz2.setColumnCount(0);
        modelMatrizResult.setRowCount(0);
        modelMatrizResult.setColumnCount(0);
    }

    /**
     * Metodo que dependiendo el boton seleccionado realiza acciones como
     * cambiar el color del boton clickeado, deshabilita todos los botones de
     * operaciones y cambia el JLabel con su operador correspondiente y en
     * ciertos casos a usa igualdad
     *
     * @param e
     */
    public void botonesOperaciones(ActionEvent e) {

        if (view.getjButtonSuma() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonSuma().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.SUMA;
            view.getjLabelOperador().setText("+");
        }
        if (view.getjButtonResta() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonResta().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.RESTA;
            view.getjLabelOperador().setText("-");
        }
        if (view.getjButtonDivision() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonDivision().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.DIVISION;
            view.getjLabelOperador().setText("/");
        }

        if (view.getjButtonDeterminante() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonDeterminante().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.DETERMINANTE;
            view.getjLabelOperador().setText(null);
        }

        if (view.getjButtonInversa() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonInversa().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.INVERSA;
            view.getjLabelOperador().setText(null);
        }

        if (view.getjButtonMultipEscalar() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonMultipEscalar().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.MULT_ESCALAR;
            view.getjLabelOperador().setText("*");
        }

        if (view.getjButtonMultiplicación() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonMultiplicación().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.MULT_MATRIZ;
            view.getjLabelOperador().setText("*");
        }

        if (view.getjButtonSolucionCramer() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonSolucionCramer().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.CRAMER;
            view.getjLabelOperador().setText("=");
        }

        if (view.getjButtonSolucionGauss() == e.getSource()) {
            deshabilitarOperaciones();
            view.getjButtonSolucionGauss().setBackground(new Color(255, 50, 50));
            tipoOperacion = TipoOperacion.GAUSS;
            view.getjLabelOperador().setText("=");
        }
    }

    /**
     * Metodo que realiza la operacion seleccionada la cuela esta guardada en la
     * variable operacion
     *
     */
    public void eventoOperaciones() {

        float[][] matrizA = leerTablaA();
        float[][] matrizB = leerTablaB();
        try {
            float[][] matrizResultado = new float[matrizA.length][matrizA[0].length];
            //
            if (tipoOperacion == TipoOperacion.SUMA) {
                matrizResultado = modelo.sumarMatrices(matrizA, matrizB, 
                        matrizA.length, matrizA[0].length);
                matrizResultado(matrizResultado);
            } else  if (tipoOperacion == TipoOperacion.RESTA) {
                matrizResultado = modelo.restarMatrices(matrizA, matrizB, 
                        matrizA.length, matrizA[0].length);
                matrizResultado(matrizResultado);
            } else  if (tipoOperacion == TipoOperacion.DIVISION) {
                matrizResultado = modelo.dividirMatrices(matrizA, matrizB, 
                        matrizA.length, matrizA[0].length);
                matrizResultado(matrizResultado);
            } else if (tipoOperacion == TipoOperacion.MULT_ESCALAR) {
                matrizResultado = modelo.multMatrizPorEscalar(matrizB[0][0],
                        matrizA, matrizA.length, matrizA[0].length);
                matrizResultado(matrizResultado);
            } else if (tipoOperacion == TipoOperacion.MULT_MATRIZ) {
                matrizResultado = modelo.productoDosMatrices(matrizA, matrizB, 
                        matrizA.length, matrizA[0].length, matrizB[0].length);
                matrizResultado(matrizResultado);
            } else if (tipoOperacion == TipoOperacion.INVERSA) {
                if (modelo.determinante(matrizA) != 0) {
                    matrizResultado = Operaciones.inversaPorGaussJordan(matrizA);
                    matrizResultado(matrizResultado);
                } else {
                    JOptionPane.showMessageDialog(null, "El determinante de la "
                            + "mtriz es 0 por lo tanto no tiene inversa");
                }
            } else if (tipoOperacion == TipoOperacion.DETERMINANTE) {
                matrizResultado[0][0] = modelo.determinante(matrizA);
                matrizResultado(matrizResultado);
            } else if (tipoOperacion == TipoOperacion.GAUSS) {
                if (modelo.determinante(matrizA) != 0) {
                    matrizResultado = Operaciones.solucionConGauss(matrizA, matrizB);
                    matrizResultado(matrizResultado);
                } else {
                    JOptionPane.showMessageDialog(null, "El determinante de la "
                            + "matriz es 0 por lo tanto existen muchas soluciones");
                }
            } else if (tipoOperacion == TipoOperacion.CRAMER) {
                if (modelo.determinante(matrizA) != 0) {
                    matrizResultado = modelo.solucionCramer(matrizA, matrizB);
                    matrizResultado(matrizResultado);
                } else {
                    JOptionPane.showMessageDialog(null, "El sistema de "
                            + "ecuaciones no puede ser resuleto por Cramer, su determinante es cero");
                }
            }

        } catch (NullPointerException e) {
        }

    }

    /**
     * Metodo que lee la primera tabla de la vista, la convierte en matriz y
     * devuelve esa misma matriz
     *
     * @return
     */
    public float[][] leerTablaA() {
        int nFilas = view.getjTableMatriz1().getRowCount();
        int nColumnas = view.getjTableMatriz1().getColumnCount();
        float[][] matriz = new float[nFilas][nColumnas];

        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                try {
                    if (String.valueOf(view.getjTableMatriz1().getValueAt(i, j)).equals("")) {
                        view.getjTableMatriz1().setValueAt(0, i, j);
                        matriz[i][j] = Float.parseFloat(String.valueOf(view.getjTableMatriz1().getValueAt(i, j)));
                    } else {
                        matriz[i][j] = Float.parseFloat(String.valueOf(view.getjTableMatriz1().getValueAt(i, j)));
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "introduzca solo numerales");
                    return null;
                }
            }
        }
        return matriz;
    }

    /**
     * Metodo que lee la segunda tabla de la vista, la convierte en matriz y
     * devuelve esa misma matriz
     *
     * @return
     */
    public float[][] leerTablaB() {
        int nFilas = view.getjTableMatriz2().getRowCount();
        int nColumnas = view.getjTableMatriz2().getColumnCount();
        float[][] matriz = new float[nFilas][nColumnas];

        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                try {
                    Float.parseFloat(String.valueOf(view.getjTableMatriz2().getValueAt(i, j)));
                    if (String.valueOf(view.getjTableMatriz2().getValueAt(i, j)).equals("")) {
                        view.getjTableMatriz2().setValueAt(0, i, j);
                        matriz[i][j] = Float.parseFloat(String.valueOf(view.getjTableMatriz2().getValueAt(i, j)));
                    } else {
                        matriz[i][j] = Float.parseFloat(String.valueOf(view.getjTableMatriz2().getValueAt(i, j)));
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "introduzca solo numerales");
                    return null;
                }
            }
        }
        return matriz;
    }

    /**
     * El metodo matrizResultado llena la tabla de resultados de la vista con
     * los resultados de la operacionrealizada.
     *
     * @param matriz
     */
    public void matrizResultado(float[][] matriz) {
        DefaultTableModel modelMatriz1 = (DefaultTableModel) view.getjTableResultados().getModel();
        JTableHeader head = view.getjTableResultados().getTableHeader();
        TableColumnModel tcm = head.getColumnModel();
            //dependiendo de la operacion seleccionada previamente se 
        //construye la matriz Resultado y se le pone los resultados obtenidos
        if (tipoOperacion == TipoOperacion.DETERMINANTE) {
            modelMatriz1.setRowCount(1);
            modelMatriz1.setColumnCount(1);
            float determinante = matriz[0][0];
            TableColumn tabCM = tcm.getColumn(0);
            tabCM.setHeaderValue("El determiante es:");
            view.getjTableResultados().setValueAt(determinante, 0, 0);
            

        } else if ((tipoOperacion == TipoOperacion.GAUSS)) {
            modelMatriz1.setRowCount(matriz.length);
            modelMatriz1.setColumnCount(1);
            TableColumn tabCM = tcm.getColumn(0);
            tabCM.setHeaderValue("Los resultados son:");
            for (int i = 0; i < matriz.length; i++) {
                view.getjTableResultados().setValueAt(matriz[i][matriz.length], i, 0);
            }
        } else if (tipoOperacion == TipoOperacion.CRAMER) {
            modelMatriz1.setRowCount(matriz.length);
            modelMatriz1.setColumnCount(1);
            TableColumn tabCM = tcm.getColumn(0);
            tabCM.setHeaderValue("Los resultados son:");
            for (int i = 0; i < matriz.length; i++) {
                view.getjTableResultados().setValueAt(matriz[i][0], i, 0);
            }
        } else {
            modelMatriz1.setRowCount(matriz.length);
            modelMatriz1.setColumnCount(matriz[0].length);

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    view.getjTableResultados().setValueAt(matriz[i][j], i, j);
                }
            }
        }

    }

}
