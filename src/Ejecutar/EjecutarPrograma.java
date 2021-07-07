/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejecutar;

import Control.ControlOperacionesMatrices;
import Modelo.Operaciones;
import Vista.VistaOperacionesMatrices;

/**
 *
 * @author LugoFrias
 */
public class EjecutarPrograma {

    public static void main(String[] args) {
        VistaOperacionesMatrices vista = new VistaOperacionesMatrices();
        Operaciones modelo = new Operaciones();
        ControlOperacionesMatrices control = new ControlOperacionesMatrices(vista, modelo);
        vista.setVisible(true);
    }
}
