/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.colores;

/**
 *
 * @author HOC
 */
public class ExcepcionAristaYaExiste extends Exception{
    public ExcepcionAristaYaExiste(){
        super("Arista ya existe");
    }

    public ExcepcionAristaYaExiste(String messaje){
        super(messaje);
    }
}

