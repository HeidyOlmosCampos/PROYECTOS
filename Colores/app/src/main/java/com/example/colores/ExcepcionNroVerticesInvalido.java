/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.colores;

/**
 *
 * @author Sebastian Padilla
 */
public class ExcepcionNroVerticesInvalido extends Exception{

    public ExcepcionNroVerticesInvalido(){
        super("Nro de vertices invalido");
    }

    public ExcepcionNroVerticesInvalido(String messaje){
        super(messaje);
    }

}

