/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

import arboles.Exception.ExceptionOrdenInvalida;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author liz fernanda reina quispert
 * @param <K>
 * @param <V>
 */
public class ArbolMVias<K extends Comparable<K>,V> 
        implements IArbolBinario<K,V> {

    protected NodoMvias<K,V> raiz;
    protected int POSICION_INVALIDA=-1;
    protected int orden;

    
    public ArbolMVias() {
        this.orden=3;
    }
    
    public ArbolMVias(int orden)throws ExceptionOrdenInvalida {
      if(orden<3){
          throw new ExceptionOrdenInvalida(); 
      }
        this.orden = orden;
    }
    
     @Override  
  public Object getRaiz(){
        return this.raiz;
    }
   
    public void setRaiz(Object nodo){
        this.raiz=(NodoMvias<K,V>)nodo;
    }
    
    @Override
     public K minimo(){
         if(esArbolVacio()){
            return null;
        }
        
          NodoMvias<K,V> nodoAnterior=( NodoMvias<K,V>)NodoMvias.nodoVacio().nodoVacio();
         NodoMvias<K,V> nodoActual=this.raiz;
       
       while(!NodoMvias.esNodoVacio(nodoActual)){
           nodoAnterior=nodoActual;
           nodoActual=nodoActual.getHijo(0);     
       }
       return nodoAnterior.getClave(0);
    }
    
    
    
    @Override
    public K maximo(){
          if(esArbolVacio()){
            return null;
        }
        
          NodoMvias<K,V> nodoAnterior=(NodoMvias<K,V>)NodoMvias.nodoVacio();
        NodoMvias<K,V> nodoActual=this.raiz;
       
       while(!NodoMvias.esNodoVacio(nodoActual)){
           nodoAnterior=nodoActual;
          nodoActual=nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());      
       }
       return nodoAnterior.getClave(nodoActual.cantidadDeClavesNoVacias());  
    }
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
       if(esArbolVacio()){
          this.raiz=new NodoMvias<>(orden,claveAInsertar,valorAInsertar);
          return ;
        }
      NodoMvias<K,V> nodoActual=this.raiz;
      while(!NodoMvias.esNodoVacio(nodoActual)){
          if(this.existeClaveEnNodo(nodoActual,claveAInsertar)!=POSICION_INVALIDA){
               throw new IllegalArgumentException("ya existe");  
          }
          //si llegamos a este punto la clave no esta en el nodo  
         if(nodoActual.esHoja()){
             if(nodoActual.estanClavesLlenas()){
             //no hay campo para clave enn este campo
                    int posicionDeDondeBajar=this.porDondeBajar(nodoActual,claveAInsertar);
                    NodoMvias<K,V> nuevoHijo=new NodoMvias<>(orden,claveAInsertar,valorAInsertar); 
                    nodoActual.setHijos(posicionDeDondeBajar,nuevoHijo);
               }else{
              // hay campo para clace_valor en este campo   
              this.insertarClaveOrdenadaEnOrden(nodoActual,claveAInsertar,valorAInsertar);
               }
             nodoActual=NodoMvias.nodoVacio();
         }else{
             int posicionDeDondeBajar=this.porDondeBajar(nodoActual,claveAInsertar);  
             if(nodoActual.esHijoVacio(posicionDeDondeBajar)){
                NodoMvias<K,V> nuevoHijo=new NodoMvias<>(orden,claveAInsertar,valorAInsertar); 
                nodoActual.setHijos(posicionDeDondeBajar,nuevoHijo); 
                nodoActual=NodoMvias.nodoVacio();
             }else{
                nodoActual=nodoActual.getHijo(posicionDeDondeBajar);
             }
         }
          
      }
    }

   public int porDondeBajar(NodoMvias<K,V> nodoActual,K claveAInsertar){
     int cantidadDatos=nodoActual.cantidadDeClavesNoVacias();
        int i=0;
        while(i<cantidadDatos){
            if(nodoActual.getClave(i).compareTo(claveAInsertar)>=0){
                return i;
            }
            
          i++;
        }
          if(!nodoActual.estanClavesLlenas()){
            return i;
        }
      
        return orden-1;         
   }
   
   public void insertarClaveOrdenadaEnOrden(NodoMvias<K,V> nodoActual,K claveAInsertar,V valorAInsertar){
      int  posicionAPoner=porDondeBajar(nodoActual, claveAInsertar);
       for (int i = nodoActual.cantidadDeClavesNoVacias()-1; i >= posicionAPoner ; i--) {
          K claveEnTurno=nodoActual.getClave(i);
           if(claveEnTurno.compareTo(claveAInsertar)>0){
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
               nodoActual.setClave(posicionAPoner,claveAInsertar);
               nodoActual.setValor(posicionAPoner,valorAInsertar);
               nodoActual.setHijos(posicionAPoner,NodoMvias.nodoVacio());
     
   }
   
    protected int existeClaveEnNodo(NodoMvias<K,V> nodoActual,K claveABuscar){
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
          K claveActual=nodoActual.getClave(i);
            if(claveABuscar.compareTo(claveActual) == 0){
                return i;
            }
        }
      return  POSICION_INVALIDA;
    }
    @Override
    public V eliminar(K claveABuscar) {
         if(claveABuscar== null ){
           throw new IllegalArgumentException("no acepta null");
                   
        }
        
      V valorARetornar=buscar(claveABuscar);
      if(valorARetornar==null){
         throw new IllegalArgumentException("no acepta null");
      }
      
      this.raiz=eliminar(this.raiz,claveABuscar);
     return valorARetornar;
    }

    public NodoMvias<K,V> eliminar(NodoMvias<K,V> nodoActual,K claveAEliminar){
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual=nodoActual.getClave(i);
            
            if(claveAEliminar.compareTo(claveActual)==0){
                if(nodoActual.esHoja()){
                    this.eliminarclaveDeNodo(nodoActual,i);
                    if(nodoActual.cantidadDeClavesNoVacias()==0){
                        return NodoMvias.nodoVacio();
                    }
                    return nodoActual;
                }
                    //si llega aqui es nodo no hoja
                K claveReemplazo;
               if(this.hayHijoMasAdelante(nodoActual,i)){
                  claveReemplazo=this.buscarClaveSucesorInOrden(nodoActual,claveAEliminar);
               }else{
                  claveReemplazo=this.buscarClavePredecesorInOrden(nodoActual,claveAEliminar);
                   
               }
               V valorReemplazo=this.buscar(claveReemplazo);
               nodoActual= eliminar(nodoActual, claveReemplazo);
               nodoActual.setClave(i, claveReemplazo);
               nodoActual.setValor(i, valorReemplazo);
               return nodoActual;
                
            }
            
            if(claveAEliminar.compareTo(claveActual)<0){
                NodoMvias<K,V> supuestoNuevoHijo=this.eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijos(i,supuestoNuevoHijo);
                return nodoActual;
             }
        }//fin for
        
                NodoMvias<K,V> supuestoNuevoHijo=this.eliminar(nodoActual.getHijo(orden-1), claveAEliminar);
                nodoActual.setHijos(orden-1,supuestoNuevoHijo);
                return nodoActual;
           
    
    }
    
   public void eliminarclaveDeNodo(NodoMvias<K,V> nodoActual,int posicion){
       nodoActual.setClave(posicion,null);
         nodoActual.setValor(posicion,null);
         
        for (int j = posicion; j <= orden - 2; j++) {
            if (nodoActual.esClaveVacia(j + 1)) {
                 return;
            }
            nodoActual.setClave(j,nodoActual.getClave(j+1));
            nodoActual.setValor(j,nodoActual.getValor(j+1));
            
            
            nodoActual.setClave(j+1,null);
            nodoActual.setValor(j+1,null);
        }
        
        
   }
   
   public boolean hayHijoMasAdelante(NodoMvias<K,V> nodoActual,int posicion){
       if(nodoActual.esHijoVacio(posicion+1) && posicion < this.orden){
           return false;
       }
     return true;
   }
   
  public K buscarClaveSucesorInOrden(NodoMvias<K,V> nodoActual,K claveAEliminar){
        List<K> recorridoInOrden=new LinkedList<>();
       recorridoInOrdenRec(nodoActual, recorridoInOrden);
       int posicionInOrden=0;
       boolean existe=false;
       for (int i = 0; i < recorridoInOrden.size() && existe== false; i++) {
           if(recorridoInOrden.get(i).compareTo(claveAEliminar)==0){
               posicionInOrden=i;
               existe=true;
           }
       }
       return recorridoInOrden.get(posicionInOrden+1);
  }
  
   public K buscarClavePredecesorInOrden(NodoMvias<K,V> nodoActual,K claveAEliminar){
         List<K> recorridoInOrden=new LinkedList<>();
       recorridoInOrdenRec(nodoActual, recorridoInOrden);
       int posicionInOrden=0;
       boolean existe=false;
       for (int i = 0; i < recorridoInOrden.size() && existe== false; i++) {
           if(recorridoInOrden.get(i).compareTo(claveAEliminar)==0){
               posicionInOrden=i;
               existe=true;
           }
       }
      int  posicion=posicionInOrden-1;
      if(posicion == -1){
         posicion=posicionInOrden+1;
      }
       return recorridoInOrden.get(posicion);
   }
   
    @Override
    public V buscar(K claveABuscar) {
         NodoMvias<K,V> nodoActual=this.raiz;
        while(!NodoMvias.esNodoVacio(nodoActual)){
            NodoMvias<K,V> nodoAnterior=nodoActual;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias()&&
                 nodoAnterior==nodoActual   ; i++) {
                K claveActual=nodoActual.getClave(i);
                 if(claveABuscar.compareTo(claveActual)==0){
                    return nodoActual.getValor(i);
                 }    
                 
                 if(claveABuscar.compareTo(claveActual)<0){
                     if(nodoActual.esHijoVacio(i)){
                         return (V)NodoMvias.datoVacio();
                     }
                   nodoActual=nodoActual.getHijo(i);
                }
            }
          if(nodoAnterior==nodoActual) { 
          nodoActual=nodoActual.getHijo(orden-1);
          }
        
        }
      return (V)nodoActual.datoVacio();
       }


    @Override
    public boolean contiene(K clave) {
        return buscar(clave)!= null;
    }

    @Override
    public int size() {
          if(esArbolVacio()){
            return 0;
        }
        
       Queue<NodoMvias<K,V>> colaDeNodos=new LinkedList<>();
       colaDeNodos.offer(this.raiz);
        int cantidad=0;
      
       
       while(!colaDeNodos.isEmpty()){
            NodoMvias<K,V> nodoActual=colaDeNodos.poll();
           for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias() ; i++) {
               cantidad++;
              if(!nodoActual.esHijoVacio(i)){
               colaDeNodos.offer(nodoActual.getHijo(i));
              }
           
           }
          
             if(!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
               colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
             }
       }
     return cantidad;
    }

    @Override
    public int altura() {
     return altura(this.raiz);
    }
    

   public int altura(NodoMvias<K,V> nodoActual){
       if(NodoMvias.esNodoVacio(nodoActual)){
           return 0;
       }
       int alturaMayor=0;
       for (int i = 0; i < orden; i++) {
           int alturaDeHijo=altura(nodoActual.getHijo(i));
           if(alturaDeHijo>alturaMayor){
               alturaMayor=alturaDeHijo;
           }
       }
         return alturaMayor+1;
       
   }


    @Override
    public void vaciar() {
        this.raiz=NodoMvias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
       return NodoMvias.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
         return  this.altura() - 1;
    }

    @Override
    public List<K> recorridoPorNIvel() {
         List<K> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        
       Queue<NodoMvias<K,V>> colaDeNodos=new LinkedList<>();
       colaDeNodos.offer(this.raiz);
       
       while(!colaDeNodos.isEmpty()){
           NodoMvias<K,V> nodoActual=colaDeNodos.poll();
           
           for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
              recorrido.add(nodoActual.getClave(i));
           
           if(!nodoActual.esHijoVacio(i)){
               colaDeNodos.offer(nodoActual.getHijo(i));
           } 
           }
                 
           if(nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
               colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
           }
           
           
       }
      return recorrido;
    }

    public List<K> recorridoPostOrdenRecursivo(){
    List<K> recorrido= new ArrayList<>();
    NodoMvias<K,V> nodoActual=this.raiz;
    recoridoPostOrdenRec(nodoActual,recorrido);
    
    return recorrido;
    
}   

void recoridoPostOrdenRec(NodoMvias<K,V> nodoActual,List<K> recorrido){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return;
    }
    
    recoridoPostOrdenRec(nodoActual.getHijo(0), recorrido);
    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
      recoridoPostOrdenRec(nodoActual.getHijo(i+1), recorrido);
      recorrido.add(nodoActual.getClave(i));     
    }
  
}

    @Override
public List<K> recorridoPreOrden(){
    List<K> recorrido= new ArrayList<>();
    NodoMvias<K,V> nodoActual=this.raiz;
    recoridoPreOrdenRec(nodoActual,recorrido);
    return recorrido;
}   

void recoridoPreOrdenRec(NodoMvias<K,V> nodoActual,List<K> recorrido){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return;
    }
    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
     recorrido.add(nodoActual.getClave(i));      
     recoridoPreOrdenRec(nodoActual.getHijo(i), recorrido); 
    }
    
    recoridoPreOrdenRec(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
}

public List<K> recorridoInOrden(){
       List<K> recorrido= new ArrayList<>();
    NodoMvias<K,V> nodoActual=this.raiz;
    recorridoInOrdenRec(nodoActual,recorrido);
    
    return recorrido;
    
}   

void recorridoInOrdenRec(NodoMvias<K,V> nodoActual,List<K> recorrido){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return;
    }
    
   
    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
     recorridoInOrdenRec(nodoActual.getHijo(i), recorrido); 
     recorrido.add(nodoActual.getClave(i));     
    }
    
    recorridoInOrdenRec(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
}
    

  
    public int cantidadDeDatosVacios() {
        return cantidadDeDatosVacios(this.raiz);
    }

    public int cantidadDeDatosVacios(NodoMvias<K,V> nodoActual){
        if(NodoMvias.esNodoVacio(nodoActual)){
            return 0;
        }
        
        int cantidad=0;
        for (int i = 0; i < orden-1; i++) {
           cantidad+= cantidadDeDatosVacios(nodoActual.getHijo(i));
             if(nodoActual.esClaveVacia(i)){
               cantidad++;
             }
        }
       cantidad+= cantidadDeDatosVacios(nodoActual.getHijo(orden-1));
       return cantidad;
    }
    
   public int cantidadDeHojas(){
       return cantidadDeHojasRec(this.raiz);
   }
   
   public int cantidadDeHojasRec(NodoMvias<K,V> nodoActual){
      if(NodoMvias.esNodoVacio(nodoActual)){
            return 0;
        }
       if(nodoActual.esHoja()){
          return 1;
        }
       
      int cantidad=0;
       for (int i = 0; i < orden; i++) {
        cantidad+= cantidadDeHojasRec(nodoActual.getHijo(i));
       
       }
       return cantidad;
   }
   
    public int cantidadDeHojasDelNivel(int nivel){
        return cantidadDeHojasDelNivelRec(this.raiz,nivel,0);
    }
    
     public int cantidadDeHojasDelNivelRec(NodoMvias<K,V> nodoActual,int nivelABuscar,int nivel){
       if(NodoMvias.esNodoVacio(nodoActual)){
           return 0;
       }
       int cantidad=0;
           if(nivelABuscar>=nivel){
          if(!nodoActual.esHoja()){
            return 1;
          }
      } 
        for (int i = 0; i < orden; i++) {
            cantidad=cantidad+cantidadDeHojasDelNivelRec(nodoActual.getHijo(i),nivelABuscar,nivel+1);
        }
       return cantidad;
    }

    @Override
    public String mostrarArbol() {
  
        return generarCadenaDeArbol(this.raiz, "", false);
    }
    
    private String generarCadenaDeArbol(NodoMvias<K,V> nodoActual,String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "└──(R)" : "├──(R)"); //arbol vacio o no
        } else {
            cadena.append(ponerCodo ? "└──(D)" : "├──(I)");  //derecha o izquierda
        }
        if (NodoMvias.esNodoVacio(nodoActual)) {
            cadena.append("╣\n");
            return cadena.toString();
        }
        
        cadena.append(nodoActual.getListaDeClaves().toString());
        cadena.append("\n");
        
        String prefijoAux = prefijo + (ponerCodo ? "   ":"|   ");
        for (int i = 0; i < this.orden - 1; i++) {
            NodoMvias<K,V> nodoIzq = nodoActual.getHijo(i);
            cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));
        }
        
        NodoMvias<K,V> nodoDer = nodoActual.getHijo(this.orden - 1);
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));

        return cadena.toString();
    }

    

    @Override
    public List<K> recorridoEnPostOrden() {
           List<K> recorrido=new LinkedList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;  
      }  
      
      public void recorridoEnPostOrden(NodoMvias<K,V> nodoActual,List<K> recorrido){
          if (NodoMvias.esNodoVacio(nodoActual)){
             return ; 
          }
          recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
          for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
               recorridoEnPostOrden(nodoActual.getHijo(i+1), recorrido);
               recorrido.add(nodoActual.getClave(i)); 
          }
          
    }
   
      
   
  /*
  1 Implementar los métodos que no se implementaron en clases o que se implementaron a medias de árboles m vias de búsqueda y arboles B

  
  
  5. Implemente un método iterativo que retorne la cantidad de datos vacios y no vacíos en un árbol b, 
    pero solo antes del nivel N 
    
    7. Implemente un método que retorne verdadero si un árbol m-vias esta balanceado según las reglas de un árbol B. Falso en caso contrario. 
    8. Implemente un método privado que reciba un dato como parámetro y que retorne cual sería el sucesor inorden de dicho dato, 
            sin realizar el recorrido en inOrden. 
      
   
  */  
    
  /*  2. Implemente un método recursivo que retorne el nivel en que se encuentra una clave que se recibe como parámetro. 
  Devolver -1 si la clave no está en el árbol */
      
  public int nivelQueEstaClave(K claveABuscar){
       return nivelQueEstaClaveRec(this.raiz,claveABuscar,0);  
    }
    
  public int nivelQueEstaClaveRec(NodoMvias<K,V> nodoActual,K claveABuscar,int nivel){
      if(NodoMvias.esNodoVacio(nodoActual)){
          return -1;
      } 
   
     for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
              if(nodoActual.getClave(i).compareTo(claveABuscar)==0){
                  return nivel;
              }
            int posible=nivelQueEstaClaveRec(nodoActual.getHijo(i),claveABuscar,nivel+1);
         if(posible!=-1){
             return posible;
         }
          }

  return -1;
  }
  
     
  // 3. Implemente un método recursivo que retorne la cantidad de datos no vacíos que hay en el nivel N de un árbol m-vias de búsqueda
        public int  cantidadNodosConDatosVacios(int nivel){    
          return cantidadNodosConDatosNoVacios(this.raiz,nivel,0);
      }
   
      private int cantidadNodosConDatosNoVacios(NodoMvias<K,V> nodoActual,int nivelABuscar,int nivelActual){
         if(NodoMvias.esNodoVacio(nodoActual)){
             return 0;
         }
       
        int cantidad=0;
        for (int i = 0; i   < orden-1; i++) {
           cantidad+=cantidadNodosConDatosNoVacios(nodoActual.getHijo(i),nivelABuscar,nivelActual+1);
          if(nivelActual==nivelABuscar){
           if(!nodoActual.esClaveVacia(i)){
               cantidad++;
             }
        }
        }
       return cantidad;
     }
  
  /* 4. Implemente un método recursivo que retorne la cantidad de nodos hojas en un árbol m vías de búsqueda,
          pero solo después del nivel N*/
        public int cantidadHojasApartirDeUnNivel(int nivel){
       return cantidadHojasApartirDeUnNivel(this.raiz,nivel,0);
   }
    private int cantidadHojasApartirDeUnNivel(NodoMvias<K,V> nodoActual,int nivelObjetivo,int nivel){
      if(NodoMvias.esNodoVacio(nodoActual)){
          return 0;
      }  
      
      if(nivel>=nivelObjetivo){
          if (nodoActual.esHoja()){
              return 1;
          }
      }
      
      int cantidad=0;
          for (int i = 0; i < orden; i++) {
              cantidad=cantidad+cantidadHojasApartirDeUnNivel(nodoActual.getHijo(i),
                      nivelObjetivo, nivel+1);
          } 
      return cantidad;
    
    }
    
//    6. Implemente un método que retorne verdadero si solo hay hojas en el último nivel de un árbol m-vias de búsqueda. Falso en caso contrario. 
       public boolean hayHojasEnElUltimoNivel(){
          return  hayHojasEnElUltimoNivel(this.raiz,1);
       }
       
      private boolean hayHojasEnElUltimoNivel(NodoMvias<K,V> nodoActual,int nivel){
           if(NodoMvias.esNodoVacio(nodoActual)){
               return false;
           }
           
            if(nivel==nivel()){
            return  nodoActual.esHoja();
            
           }
            
          for (int i = 0; i < orden; i++) {
              return hayHojasEnElUltimoNivel(nodoActual.getHijo(i),nivel+1);
          }
          
      return true;
       }
//

public int cantDatos(){
    return cantDatosRec(this.raiz);
}      
public int cantDatosRec(NodoMvias<K,V> nodoActual){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return 0;
    }
    int ac = 0;
    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {     
     ac += cantDatosRec(nodoActual.getHijo(i))+1; 
    }
    
    return cantDatosRec(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()))+ac;
}

public int cantidadClavesVacias(){
    return cantidadClavesVaciasRec(this.raiz);
}      
public int cantidadClavesVaciasRec(NodoMvias<K,V> nodoActual){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return 0;
    }
    int cantidad = 0;
    for (int i = 0; i < orden-1; i++) {       
     if(nodoActual.getClave(i)==null){
      cantidad += cantidadClavesVaciasRec(nodoActual.getHijo(i))+1; 
     }
      cantidad += cantidadClavesVaciasRec(nodoActual.getHijo(i));
    }
    
    return cantidadClavesVaciasRec(nodoActual.getHijo(orden-1))+cantidad;
}


public int cantidadDeNodosPadres(int nivel){
    return cantidadDeNodosPadresRed(this.raiz,nivel,0);
}

public int cantidadDeNodosPadresRed(NodoMvias<K,V> nodoActual,int nivelABuscar,int nivel){
    if(NodoMvias.esNodoVacio(nodoActual)){
        return 0;
    }
    
      if(nivelABuscar!=nivel){
            if(!nodoActual.esHoja()){
                return 1;
            }
            return 0;
        }
      
    int cantidad=0;
    for (int i = 0; i < orden; i++) {
        cantidad+=cantidadDeNodosPadresRed(nodoActual.getHijo(i),nivelABuscar,nivel+1);
     
    }
    
    return cantidad;
}

    @Override
    public void insertarIterativo(K clave, V valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
