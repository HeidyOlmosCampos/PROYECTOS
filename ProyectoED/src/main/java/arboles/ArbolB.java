/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

import arboles.Exception.ExceptionOrdenInvalida;
import java.util.Stack;

/**
 *
 * @author brandon
 * @param <K>
 * @param <V>
 */
public class ArbolB<K extends Comparable<K>,V> extends ArbolMVias<K,V>{
    private int nroMaximoDeDatos;
    private int nroMinimoDeDatos;
    private int nroMinimoDeHijos;

    public ArbolB() {
        super();
        nroMaximoDeDatos=2;
        nroMinimoDeDatos=1;
        nroMinimoDeHijos=2;
                
    }
    
      public ArbolB(int orden) throws ExceptionOrdenInvalida {
        super(orden);
        nroMaximoDeDatos=super.orden-1;
        nroMinimoDeDatos=this.nroMaximoDeDatos/2;
        nroMinimoDeHijos=this.nroMinimoDeDatos+1;
 
    }
      
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
      if (this.esArbolVacio()) {
            this.raiz = new NodoMvias<>(this.orden + 1,claveAInsertar,valorAInsertar);
            return;
        }
        Stack<NodoMvias<K,V>> pilaDeAncestros = new Stack<>();
        NodoMvias<K,V> nodoActual = this.raiz;
        while (!NodoMvias.esNodoVacio(nodoActual)) {            
            if (this.existeClaveEnNodo(nodoActual, claveAInsertar) > -1) {
                return;
            }
            if (nodoActual.esHoja()) {
                //siempre habra campo
                this.insertarOrdenB(nodoActual, claveAInsertar,valorAInsertar);
                if (nodoActual.cantidadDeClavesNoVacias() > this.nroMaximoDeDatos) {
                    dividir(nodoActual, pilaDeAncestros);
                }
                //esto es para que termine el ciclo
                nodoActual = NodoMvias.nodoVacio();
            } else {
                //bajamos guardando los ancestros en la pila
                int posicion = this.porDondeBajar(nodoActual, claveAInsertar);
                pilaDeAncestros.push(nodoActual);
                nodoActual = nodoActual.getHijo(posicion);
            }
        }//fin While
        return;
}
    private void dividir(NodoMvias<K,V> nodoActual, Stack<NodoMvias<K,V>> pilaDePadres) {
        NodoMvias<K,V> nuevoNodoIzq = new NodoMvias<>(this.orden + 1);
        NodoMvias<K,V> nuevoNodoDer = new NodoMvias<>(this.orden + 1);
        //salvamos la clave del nodo a dividir
        K claveASubir = nodoActual.getClave(this.nroMinimoDeDatos);
        V valorASubir = nodoActual.getValor(this.nroMinimoDeDatos);
        //cargamos el nodo Izq
        for (int i = 0; i < this.nroMinimoDeDatos; i++) {
            nuevoNodoIzq.setClave(i, nodoActual.getClave(i));
            nuevoNodoIzq.setValor(i, nodoActual.getValor(i));
            nuevoNodoIzq.setHijos(i, nodoActual.getHijo(i));
        }
        nuevoNodoIzq.setHijos(this.nroMinimoDeDatos, nodoActual.getHijo(this.nroMinimoDeDatos));
        //cargamos el nodo Der
        int posicionAux = 0;
        for (int i = this.nroMinimoDeDatos + 1; i < this.orden; i++) {
            nuevoNodoDer.setClave(posicionAux, nodoActual.getClave(i));
            nuevoNodoDer.setValor(posicionAux, nodoActual.getValor(i));
            nuevoNodoDer.setHijos(posicionAux, nodoActual.getHijo(i));
            posicionAux++;
        }
        nuevoNodoDer.setHijos(posicionAux, nodoActual.getHijo(this.orden));
        //verificamos si el nodo en el que estamos es el raiz
        if (pilaDePadres.isEmpty()) { //es la raiz por que ya no hay nada que sacar
            //creamos un nuevo nodo para que el sea la nueva raiz
           NodoMvias<K,V> nuevaRaiz = new NodoMvias<>(this.orden + 1, claveASubir,valorASubir);
            this.raiz =nuevaRaiz; //la asignamos como la nueva raiz
            nuevaRaiz.setHijos(0, nuevoNodoIzq);
            nuevaRaiz.setHijos(1, nuevoNodoDer);
        } else { //el nodo no es la raiz
            //sacamos el nodo padre del nodo Actual
            NodoMvias<K,V> nodoPadre = pilaDePadres.pop();
            //buscamos la posicion por la que colgaba el nodoActual
            int posicionDelNodoActualEnElPadre = this.porDondeBajar(nodoPadre, claveASubir);
            //ahora insertamos el datoASubir en el nodoActual desplazando los otros si es posible
            insertarOrdenB(nodoPadre, claveASubir,valorASubir);
            nodoPadre.setHijos(posicionDelNodoActualEnElPadre, nuevoNodoIzq);
            nodoPadre.setHijos(posicionDelNodoActualEnElPadre + 1, nuevoNodoDer);
            if (nodoPadre.cantidadDeClavesNoVacias() > this.nroMaximoDeDatos) {
                dividir(nodoPadre, pilaDePadres);
            }
        }
    }
    
    
      public void insertarOrdenB(NodoMvias<K,V> nodoActual,K clave,V valor){  
     int  posicionAPoner=porDondeBajar(nodoActual, clave);
       for (int i = nodoActual.cantidadDeClavesNoVacias()-1; i >= posicionAPoner ; i--) {
          K claveEnTurno=nodoActual.getClave(i);
           if(claveEnTurno.compareTo(clave)>0){
               if(hayHijoMasAdelante(nodoActual, i) && nodoActual.esClaveVacia(i+1)){
                   nodoActual.setClave(i+1, nodoActual.getClave(i));
                   nodoActual.setValor(i+1, nodoActual.getValor(i));
                   nodoActual.setHijos(i+2, nodoActual.getHijo(i+1));
                   nodoActual.setHijos(i+1, nodoActual.getHijo(i));
                  
               }else{
               nodoActual.setClave(i+1, nodoActual.getClave(i));
               nodoActual.setValor(i+1, nodoActual.getValor(i));
               nodoActual.setHijos(i+1, nodoActual.getHijo(i));
                 }
           }
       }
               nodoActual.setClave(posicionAPoner,clave);
               nodoActual.setValor(posicionAPoner,valor);
               nodoActual.setHijos(posicionAPoner,NodoMvias.nodoVacio());
       
   }

 @Override
public V eliminar(K claveABuscar) {
   Stack<NodoMvias<K,V>> pilaDeAncestros = new Stack<>();
        NodoMvias<K,V> nodoActual = buscarNodoDelclave(claveABuscar,
                pilaDeAncestros);//nodo del dato a eliminar
        if (NodoMvias.esNodoVacio(nodoActual)) {
            return (V) NodoBinario.nodoVacio(); // el dato no esta(no existe)
        }
        
        int posicionDelClaveEnElNodo = 
                this.porDondeBajar(nodoActual, claveABuscar);
        V valorABuscar=nodoActual.getValor(posicionDelClaveEnElNodo);
        if (nodoActual.esHoja()) {
            super.eliminarclaveDeNodo(nodoActual, posicionDelClaveEnElNodo);
            
            if (nodoActual.cantidadDeClavesNoVacias() < this.nroMinimoDeDatos) {
                if (pilaDeAncestros.isEmpty()) {
                    //si se cumple es la raiz
                    if (nodoActual.cantidadDeClavesNoVacias() == 0) {
                        this.vaciar();
                    }
                } else {
                    prestarOFusionar(nodoActual, pilaDeAncestros);
                }
            }
        } else {
            NodoMvias<K,V> nodoDelpredecesor = 
                    this.buscarNodoDelPredecesorInOrden(nodoActual,
                            claveABuscar, pilaDeAncestros);
            int posicionDelPredecesor =
                    nodoDelpredecesor.cantidadDeClavesNoVacias() - 1;
            K claveDelPredecesor = nodoDelpredecesor.getClave(posicionDelPredecesor);
            V valorDelPredecesor = nodoDelpredecesor.getValor(posicionDelPredecesor);
            
            nodoActual.setClave(posicionDelClaveEnElNodo, claveDelPredecesor);
            nodoActual.setValor(posicionDelClaveEnElNodo, valorDelPredecesor);
            
            super.eliminarclaveDeNodo(nodoDelpredecesor, posicionDelPredecesor);
            if (nodoDelpredecesor.cantidadDeClavesNoVacias() < this.nroMinimoDeDatos) {
                prestarOFusionar(nodoDelpredecesor, pilaDeAncestros);
            }
        }
        return valorABuscar;
    }
    
  /**
     * metodo que recorre el arbol buscando el nodo en que se encuentra el<br>
     * dato a eliminar, mientras busca mete a la pila todos los nodos que<br>
     * son antecesores al nodo del dato a eliminar, de modo que cuando termina<br>
     * la pila ya esta llena con todos los ancestros del nodo a eliminar,<br>
     * en caso de no encontrar dicho nodo, se debera retornar un nodo vacio<br>
     * haciendo referencia a que no se encontro dicho nodo
     * @param datoAEliminar dato que se buscara en el arbol
     * @param pilaDeAncestros pila que contendra todos los ancestros del nodo que<br>
     *                          contiene al dato a eliminar
     * @return nodo del dato a eliminar, si no lo encuentra retornada nodo vacio
     */

   @Override
    public V buscar(K claveABuscar) {
          Stack<NodoMvias<K,V>> pilaDeAncestros = new Stack<>();
          NodoMvias<K,V> nodoActual=buscarNodoDelclave(claveABuscar,
                pilaDeAncestros);
         if(nodoActual != null){
              for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
             if(claveABuscar.compareTo(nodoActual.getClave(i)) == 0){
                 return nodoActual.getValor(i);
             }
            }
          }
          
       return null; 
    }

    private NodoMvias<K,V> buscarNodoDelclave(K claveAEliminar, Stack<NodoMvias<K,V>> pilaDeAncestros) {
        NodoMvias<K,V> nodoActual = this.raiz;
        while (!NodoMvias.esNodoVacio(nodoActual)) {            
            NodoMvias<K,V> nodoAnterior = nodoActual;
            //recorro todos los datos con sus correspondientes hijos
            for (int i = 0; i < this.orden - 1 && (nodoActual == nodoAnterior); i++) {
                if (nodoActual.getClave(i) != ((K) NodoMvias.datoVacio())) {
                    K claveActual = nodoActual.getClave(i);
                    if (claveActual.compareTo(claveAEliminar) == 0) { //lo encontre
                        return nodoActual;
                    }
                    if (claveActual.compareTo(claveAEliminar) > 0) { //tengo que bajar
                        pilaDeAncestros.push(nodoActual);
                        if (nodoActual.esHijoVacio(i)) {
                            return NodoMvias.nodoVacio();
                        }
                        nodoActual = nodoActual.getHijo(i);
                    }
                } else {
                    pilaDeAncestros.push(nodoActual);
                    if (nodoActual.esHijoVacio(i)) {
                        return NodoMvias.nodoVacio();
                    }
                    nodoActual = nodoActual.getHijo(i);
                }
            }
            //si no lo encontre en todos los datos del nodo, reviso si sigo
            //en el nodo actual para irme por el ultimo hijo
            if (nodoActual == nodoAnterior) {
                pilaDeAncestros.push(nodoActual);
                nodoActual = nodoActual.getHijo(this.orden - 1);
            }
        }
        return nodoActual; //es nodo vacio
    }
    ///////////////////////////////////////////////////////////////////////   

   
      
 //////////////////////////////////////////////////////////////////////////////////   
     private void prestarOFusionar(NodoMvias<K,V> nodoActual, Stack<NodoMvias<K,V>> pilaDeAncestros) {
        NodoMvias<K,V> nodoPadreDelNodoActual = pilaDeAncestros.pop();
        int posicionDelNodoActualEnelPadre = this.posicionDelNodoActualEnPadre(nodoPadreDelNodoActual,nodoActual);
        //primero vere si me puedo prestar
        boolean mePreste = false;
        //tengo hermano a mi Derecha?
        if (!nodoPadreDelNodoActual.esHijoVacio(posicionDelNodoActualEnelPadre + 1)) { //si existe hermano derecho
            NodoMvias<K,V> hermanoDerecho = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre + 1); //lo sacamos
            //puedo prestarme del siguiente?
            if (hermanoDerecho.cantidadDeClavesNoVacias() > this.nroMinimoDeDatos) { //me puede prestar?
                K datoDelHermanoDerecho = hermanoDerecho.getClave(0);
                V valorDelHermanoDerecho = hermanoDerecho.getValor(0);
                
                NodoMvias<K,V> hijoDelHermanoDerecho = hermanoDerecho.getHijo(0);
                eliminarPosicionDeDatoEHijo(hermanoDerecho, 0); //elimina el primer dato e hijo del hermanoDerecho
                K datoDelPadre = nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre);
                V valorDelPadre = nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre);
                
                int posicionAInsertarDatoEnNodoActual = nodoActual.cantidadDeClavesNoVacias();
                nodoPadreDelNodoActual.setClave(posicionDelNodoActualEnelPadre, datoDelHermanoDerecho);
                nodoPadreDelNodoActual.setValor(posicionDelNodoActualEnelPadre, valorDelHermanoDerecho);
                
                nodoActual.setClave(posicionAInsertarDatoEnNodoActual, datoDelPadre);
                nodoActual.setValor(posicionAInsertarDatoEnNodoActual, valorDelPadre);
                
                nodoActual.setHijos(posicionAInsertarDatoEnNodoActual + 1, hijoDelHermanoDerecho);
                mePreste = true;
            }
        }
        if (!mePreste && (posicionDelNodoActualEnelPadre != 0)) { //tendre hermano a la izquierda
        NodoMvias<K,V> hermanoIzquierdo = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre - 1);
            //puedo prestarme del anterior?
            if (hermanoIzquierdo.cantidadDeClavesNoVacias()> this.nroMinimoDeDatos) {
                int posicionDelUltimoDatoDelHermanoIzquierdo = hermanoIzquierdo.cantidadDeClavesNoVacias()- 1;
                K datoDelHermanoIzquierdo = hermanoIzquierdo.getClave(posicionDelUltimoDatoDelHermanoIzquierdo);
                V valorDelHermanoIzquierdo = hermanoIzquierdo.getValor(posicionDelUltimoDatoDelHermanoIzquierdo);
                
                NodoMvias<K,V> hijoDelHermanoIzquierdo = hermanoIzquierdo.getHijo(posicionDelUltimoDatoDelHermanoIzquierdo + 1);
                eliminarPosicionDeDatoEHijo(hermanoIzquierdo, posicionDelUltimoDatoDelHermanoIzquierdo + 1); //elimina solo el hijo
                hermanoIzquierdo.setClave(posicionDelUltimoDatoDelHermanoIzquierdo, (K)NodoMvias.datoVacio()); //ahora elimino el dato
                hermanoIzquierdo.setValor(posicionDelUltimoDatoDelHermanoIzquierdo, (V)NodoMvias.datoVacio()); //ahora elimino el dato
                
                K datoDelPadre = nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre - 1); //sacamos el dato del padre
                V valorDelPadre = nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre - 1); //sacamos el valor del padre
                nodoPadreDelNodoActual.setClave(posicionDelNodoActualEnelPadre - 1, datoDelHermanoIzquierdo); //le ponemos el dato del hermano
                nodoPadreDelNodoActual.setValor(posicionDelNodoActualEnelPadre - 1, valorDelHermanoIzquierdo); //le ponemos el dato del hermano
                
                this.insertarPrimerDatoEHijo(nodoActual, hijoDelHermanoIzquierdo , datoDelPadre,valorDelPadre); //insertamos lo prestado
                mePreste = true;
            }
        }
        //si no me preste me tengo que fusionar
        if (!mePreste) {
            //puedo fusionarme con el hermano Derecho?
            if (!nodoPadreDelNodoActual.esHijoVacio(posicionDelNodoActualEnelPadre + 1)) {
              NodoMvias<K,V> hermanoDerecho = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre + 1);
               NodoMvias<K,V> nuevoHijo = new NodoMvias<>(this.orden + 1);
                //cargo el nuevo hijo
                //con los datos del nodoActual
                int posicionActualDelNuevoHijo = 0;
                for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoActual.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoActual.getValor(i));
                    
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre));
                nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre));
                
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(posicionActualDelNuevoHijo));
                eliminarPosicionDeDatoEHijo(nodoPadreDelNodoActual, posicionDelNodoActualEnelPadre);
                posicionActualDelNuevoHijo++; //hacemos espacio para los datos e hijos del hermnano Derecho
                //ahora cargamos el hermanoDerecho
                int cantidadDeDatosDelHermanoDerecho = hermanoDerecho.cantidadDeClavesNoVacias();
                for (int i = 0; i < cantidadDeDatosDelHermanoDerecho; i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, hermanoDerecho.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, hermanoDerecho.getValor(i));
                    
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoDerecho.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoDerecho.getHijo(cantidadDeDatosDelHermanoDerecho));
                nodoPadreDelNodoActual.setHijos(posicionDelNodoActualEnelPadre, nuevoHijo);
            } else if (posicionDelNodoActualEnelPadre != 0) {
                NodoMvias<K,V> hermanoIzquierdo = nodoPadreDelNodoActual.getHijo(posicionDelNodoActualEnelPadre - 1);
                NodoMvias<K,V> nuevoHijo = new NodoMvias<>(this.orden + 1);
                //cargamos al nuevohijo
                //primero El hermanoIzquierdo
                int posicionActualDelNuevoHijo = 0;
                for (int i = 0; i < hermanoIzquierdo.cantidadDeClavesNoVacias(); i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, hermanoIzquierdo.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, hermanoIzquierdo.getValor(i));
                    
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoIzquierdo.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getClave(posicionDelNodoActualEnelPadre - 1));
                nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoPadreDelNodoActual.getValor(posicionDelNodoActualEnelPadre - 1));
                
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, hermanoIzquierdo.getHijo(posicionActualDelNuevoHijo));
                eliminarPosicionDeDatoEHijo(nodoPadreDelNodoActual, posicionDelNodoActualEnelPadre - 1);
                posicionActualDelNuevoHijo++;
                //ahora cargamos el nodoActula
                int cantidadDeDatosDelNodoActual = nodoActual.cantidadDeClavesNoVacias();
                for (int i = 0; i < cantidadDeDatosDelNodoActual; i++) {
                    nuevoHijo.setClave(posicionActualDelNuevoHijo, nodoActual.getClave(i));
                    nuevoHijo.setValor(posicionActualDelNuevoHijo, nodoActual.getValor(i));
                    
                    nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(i));
                    posicionActualDelNuevoHijo++;
                }
                nuevoHijo.setHijos(posicionActualDelNuevoHijo, nodoActual.getHijo(cantidadDeDatosDelNodoActual));
                nodoPadreDelNodoActual.setHijos(posicionDelNodoActualEnelPadre - 1, nuevoHijo);
            }
        }
        if (!pilaDeAncestros.isEmpty()) {
            if (nodoPadreDelNodoActual.cantidadDeClavesNoVacias()< this.nroMinimoDeDatos) {
                prestarOFusionar(nodoPadreDelNodoActual, pilaDeAncestros);
            }
        } else {
            if (nodoPadreDelNodoActual.cantidadDeClavesNoVacias()== 0) { //cambiamos de raiz
                this.raiz = nodoPadreDelNodoActual.getHijo(0);
            }
        }
    }
    
      /**
     * metodo que busca el nodo en que se encuentra el dato del predecesor,<br>
     * mientras lo busca baja por las posiciones necesarias hasta encontrarlo,<br>
     * si o si lo encuentra devido a que ya hicimos las validaciones necesarias<br>
     * para saber que ese nodo si existe
     * @param nodoActual nodo en que se empezara la busqueda
     * @param datoAEliminar dato por el cual se busca su predecesor
     * @param pilaDeAncestros pila que guardara todos los ancestros del nodo<br>
     *      predecesor, incluido el nodoActual
     * @return el nodo en que se encuentra el dato del predecesor
     */
    private NodoMvias<K,V> buscarNodoDelPredecesorInOrden(NodoMvias<K,V> nodoActual, K claveAEliminar, Stack<NodoMvias<K,V>> pilaDeAncestros) {
        if (!nodoActual.esHoja()) {
            pilaDeAncestros.push(nodoActual);
            if (this.existeClaveEnNodo(nodoActual, claveAEliminar) > -1) {
                int posicionPorDondeBajaElPredecesor = this.porDondeBajar(nodoActual, claveAEliminar); //le ponemos el -1 porque al mandarle un dato existente se salta esa posicion
                return this.buscarNodoDelPredecesorInOrden(nodoActual.getHijo(posicionPorDondeBajaElPredecesor), claveAEliminar, pilaDeAncestros);
            } else {
                for (int i = this.orden - 1; i >= 0; i--) {
                    if (!nodoActual.esHijoVacio(i)) {
                        return this.buscarNodoDelPredecesorInOrden(nodoActual.getHijo(i), claveAEliminar, pilaDeAncestros);
                    }
                }
            }
        }
        return nodoActual;
    }

    /**
     * metodo que me proporcionara la posicion de hijo del nodoPadreDelNodoActual<br>
     * de ala que cuelga el nodoActual<br>
     * Nota.- al recorrer el for si o si retornara la posicion en que se encuentra<br>
     * el nodoActual devido a que le estamos mandando uno de sus hijos, pero para<br>
     * evitar que lo pinte de rojo por el supuesto error que no existe al terminar<br>
     * el ciclo retornaremos el ultimo hijo.
     * @param nodoPadreDelNodoActual el padre del nodoActual
     * @param nodoActual nodo del que le buscaremos su posicion
     * @return la posicion de la que cuelga el nodoActual
     */
    private int posicionDelNodoActualEnPadre(NodoMvias<K,V> nodoPadreDelNodoActual,NodoMvias<K,V> nodoActual) {
        for (int i = 0; i < this.orden - 1; i++) { //si o si sera uno de los hijos
            if (nodoPadreDelNodoActual.getHijo(i) == nodoActual) {
                return i;
            }
        }
        return this.orden - 1;
    }
    
    /**
     * metodo que elimina un dato e hijo que se encuentran en una posicion determinada
     * @param nodoActual nodo del cual se eliminara el dato e hijo
     * @param posicionAEliminar posicion real en el nodo
     */
    private void eliminarPosicionDeDatoEHijo(NodoMvias<K,V> nodoActual, int posicionAEliminar) {
        for (int i = posicionAEliminar; i < this.orden - 1; i++) {
            nodoActual.setClave(i, nodoActual.getClave(i + 1));
            nodoActual.setValor(i, nodoActual.getValor(i + 1));
            nodoActual.setHijos(i, nodoActual.getHijo(i + 1));
        }
        nodoActual.setHijos(this.orden - 1, NodoMvias.nodoVacio());
    }
    
    /**
     * inserta el primer dato e hijo en el nodoActual
     * @param nodoActual nodo en que se insertara
     * @param hijoDelHermanoIzquierdo es el ultimo hijo del hermano de la izquierda
     * @param datoDelPadre dato que cae del padre al reemplazar el dato del<br>
     *      hermano izquierdo
     */
    private void insertarPrimerDatoEHijo(NodoMvias<K,V> nodoActual, NodoMvias<K,V> hijoDelHermanoIzquierdo, K claveDelPadre,V valorDelPadre) {
        int posicionDelUltimoDatoDelNodoActual = nodoActual.cantidadDeClavesNoVacias() - 1;
        for (int i = posicionDelUltimoDatoDelNodoActual; i >= 0 ; i--) {
            nodoActual.setClave(i + 1, nodoActual.getClave(i));
            nodoActual.setValor(i + 1, nodoActual.getValor(i));
            nodoActual.setHijos(i + 2, nodoActual.getHijo(i + 1));
        }
        nodoActual.setHijos(1, nodoActual.getHijo(0));
        nodoActual.setClave(0, claveDelPadre);
        nodoActual.setValor(0, valorDelPadre);
        nodoActual.setHijos(0, hijoDelHermanoIzquierdo);
    }

}
