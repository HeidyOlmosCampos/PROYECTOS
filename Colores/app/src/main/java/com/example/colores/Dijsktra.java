package com.example.colores;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Personal
 */
public class Dijsktra {
    protected GrafoPesado grafo;
    protected UtilsRecorridos marcados;
    protected Costos costos;
    protected Predecesores predecesores;
    protected int posDestino;

    public Dijsktra(GrafoPesado unGrafo, int posOrigen, int posDest){
        this.grafo = unGrafo;
        grafo.validarVertice(posOrigen); //validando que el vertice exista en el grafo
        grafo.validarVertice(posDestino); //validando que el vertice exista en el grafo

        int cantDeVertices = grafo.cantidadDeVertices();

        posDestino = posDest;
        marcados = new UtilsRecorridos(cantDeVertices);
        marcados.desmarcarTodos();
        costos = new Costos(cantDeVertices, posOrigen);
        predecesores = new Predecesores(cantDeVertices);
        algoritmoDijktra();

    }

    private void algoritmoDijktra(){
        while(!marcados.estaMarcado(posDestino)){
            int verticeEnTurno = verticeNoMarcadoDeMenorCosto();

            if(verticeEnTurno == -1){//si es -1 quiere decir que se encontro un vertice no marcado con costo infinito
                throw new IllegalArgumentException("No se puede llegar al destino");
            }
            marcados.marcarVertice(verticeEnTurno);
            double costoVerticeEnTurno = costos.getCosto(verticeEnTurno);
            Iterable<Integer> adyacentesDeVertice = grafo.adyacentesDeVertice(verticeEnTurno);

            for(Integer verticeAdyacente : adyacentesDeVertice){
                if(!marcados.estaMarcado(verticeAdyacente)){
                    double costoVerticeAydacente = costos.getCosto(verticeAdyacente);
                    double pesoDeAdyacente = getPeso(verticeEnTurno, verticeAdyacente);
                    double costoAcumulado = costoVerticeEnTurno + pesoDeAdyacente;
                    if(costoVerticeAydacente > costoAcumulado){
                        costos.setCosto(verticeAdyacente, costoAcumulado);
                        predecesores.setPredecesor(verticeAdyacente, verticeEnTurno);
                    }
                }
            }
        }
    }


    public double costoMinimo(){
        return costos.getCosto(posDestino);
    }


    public Iterable<Integer> caminoDeCostoMinimo(){
        List<Integer> caminoMin = new ArrayList<>();
        int pos = posDestino;
        Stack<Integer> camino = new Stack<>();
        while(predecesores.getPredecesor(pos) != -1){
            camino.push(pos);
            pos = predecesores.getPredecesor(pos);
        }
        camino.push(pos);

        while(!camino.isEmpty()){
            caminoMin.add(camino.pop());
        }
        return caminoMin;
    }


    private double getPeso(int posVertice, int posAdyacente){
        grafo.validarVertice(posVertice);
        grafo.validarVertice(posAdyacente);
        List<AdyacenteConPeso> adyacentesDeVertice = grafo.getAdyacentesDeVertice(posVertice);
        for(AdyacenteConPeso verticeAdyacente : adyacentesDeVertice){
            if(verticeAdyacente.getIndiceVertice() == posAdyacente)
                return verticeAdyacente.getPeso();
        }

        return -1; //cuando no existe adyacencia
    }




    private int verticeNoMarcadoDeMenorCosto(){
        Iterable<Integer> noMarcados = marcados.noMarcados();
        double menor = Double.MAX_VALUE;
        int vMenor = -1;
        for(Integer vert : noMarcados){
            double costoVertice = costos.getCosto(vert);
            if(costoVertice < menor){
                menor = costoVertice;
                vMenor = vert;
            }
        }
        return vMenor; //si es -1 quiere decir que se encontro un vertice no marcado con costo infinito
    }
}

