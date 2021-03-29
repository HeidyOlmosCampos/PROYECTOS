/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuArboles;

import arboles.ArbolAVL;
import arboles.ArbolB;
import arboles.ArbolBusquedaBinaria;
import arboles.ArbolMVias;
import arboles.Exception.ExceptionOrdenInvalida;
import arboles.IArbolBinario;
import arboles.NodoBinario;
import arboles.NodoMvias;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.tools.StandardLocation;

/**
 *
 * @author brandon
 */
public class MainArboles extends javax.swing.JFrame {
 private JFileChooser openFile;
  private IArbolBinario<String,String> ArbolDeBusqueda = null;
  private IArbolBinario<String,String> ArbolBinario = null;
  private IArbolBinario<String,String> ArbolAvl = null;
  private IArbolBinario<String,String> ArbolMvias = null;
  private IArbolBinario<String,String> ArbolB= null;
  
    /**
     * Creates new form MainArboles
     */
    public MainArboles() {
        initComponents();
         //Color JFrame
        this.getContentPane().setBackground(Color.pink);
        this.textField1.setBackground(Color.LIGHT_GRAY);
        this.textField2.setBackground(Color.LIGHT_GRAY);
       this.textArea1.setBackground(Color.LIGHT_GRAY);
        
          jComboBox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                //Do Something
                  FileInputStream file =null;
                   File f;
                
                 if (evt.getStateChange() == ItemEvent.SELECTED) {     
                      
                      try {
                            switch(jComboBox1.getSelectedIndex()){
                              case(0)://ABB

                                  break;
                              case(1)://ABB
                                    ArbolDeBusqueda=prepararArbolBinario();// me prepara el arbol creando un archivo para que guarde las palabras
                                  break;

                              case(2)://AVL
                                    ArbolDeBusqueda=prepararArbolAvl();  
                                
                              break;


                              case(3)://AMV
                                    ArbolDeBusqueda=prepararArbolMvias();
                                
                                  break;

                                 case(4)://AB
                                    ArbolDeBusqueda=prepararArbolB();
                                  break;
                                  
                              default:
                                  
                          }
                           
                            if(ArbolDeBusqueda != null){
                            textArea1.setText(ArbolDeBusqueda.mostrarArbol()); 
                            }
                           
                      } catch (Exception e) {
                          
                      }
   
                    // Item was just selected
                  } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
                    // Item is no longer selected
                  }
                              
            }
        });   
    }

    public IArbolBinario<String,String> prepararArbolBinario(){
   
    if (ArbolBinario==null){
         ArbolBinario=new ArbolBusquedaBinaria<>();

               ArbolBinario.insertar("hola".toUpperCase(), "expresión usada para saludar informalmente ");
               ArbolBinario.insertar("abeja".toUpperCase(), "insecto que da miel");
               ArbolBinario.insertar("silla".toUpperCase(), "objeto para sentarse");
               ArbolBinario.insertar("luz".toUpperCase(), "aparato que se usa para alumbrar");
     
     } 
      
//        FileInputStream file =null;
//       File f;
//        try {
//             NodoBinario nodoBinario;
//           f = new File("binario.obj");
//           if (ArbolBinario==null && f.isFile() && f.canRead()) {
//           
//                file=  new FileInputStream(f);
//           
//            ObjectInputStream tuberia=new ObjectInputStream(file);
//            nodoBinario= (NodoBinario)tuberia.readObject();
//            ArbolBinario=new ArbolBusquedaBinaria<>();
//            ArbolBinario.setRaiz(nodoBinario);          
//     }else if (ArbolBinario==null){
//         ArbolBinario=new ArbolBusquedaBinaria<>();
//
//     } 
//        } catch (Exception e) {
//        }
//       
         return ArbolBinario;
    }
    
      public IArbolBinario<String,String> prepararArbolAvl(){
          
         if (ArbolAvl==null){
           ArbolAvl=new ArbolAVL<>();

             ArbolAvl.insertar("hola".toUpperCase(), "expresión usada para saludar informalmente ");
                  ArbolAvl.insertar("abeja".toUpperCase(), "insecto que da miel");
                  ArbolAvl.insertar("silla".toUpperCase(), "objeto para sentarse");
                  ArbolAvl.insertar("luz".toUpperCase(), "aparato que se usa para alumbrar");
            
       } 
         
         
//        FileInputStream file =null;
//       File f;
//        try {
//         NodoBinario nodoAvl;
//        f = new File("avl.obj");
//       if (ArbolAvl==null && f.isFile() && f.canRead()) {
//              file=  new FileInputStream(f);
//              ObjectInputStream tuberia=new ObjectInputStream(file);
//              nodoAvl= (NodoBinario)tuberia.readObject();
//              ArbolAvl=new ArbolAVL<>();
//              ArbolAvl.setRaiz(nodoAvl);          
//       }else if (ArbolAvl==null){
//           ArbolAvl=new ArbolAVL<>();
//
//       }
//    
//        } catch (Exception e) {
//        }
       
         return ArbolAvl;
      }
      
        public IArbolBinario<String,String> prepararArbolMvias() {
            if (ArbolMvias==null){
                 JTextField orden = new JTextField();
       
                Object[] message = {
               "orden:", orden,
                } ;  
            orden.setText("4");
                    JOptionPane.showConfirmDialog(null, message, "arbol Mvias se requiere el orden", JOptionPane.OK_CANCEL_OPTION);
                try {
                    ArbolMvias=new ArbolMVias<>(Integer.parseInt(orden.getText()));
                } catch (ExceptionOrdenInvalida ex) {
                    Logger.getLogger(MainArboles.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            
             ArbolMvias.insertar("hola".toUpperCase(), "expresión usada para saludar informalmente ");
                  ArbolMvias.insertar("abeja".toUpperCase(), "insecto que da miel");
                  ArbolMvias.insertar("silla".toUpperCase(), "objeto para sentarse");
                  ArbolMvias.insertar("luz".toUpperCase(), "aparato que se usa para alumbrar");
            
            }
//        FileInputStream file =null;
//        File f;
//        try {
//                NodoMvias nodoMvias;
//                f = new File("m-vias.obj");
//                if (ArbolMvias==null && f.isFile() && f.canRead()) {
//                       file=  new FileInputStream(f);
//                       ObjectInputStream tuberia=new ObjectInputStream(file);
//                       nodoMvias= (NodoMvias)tuberia.readObject();
//                       
//                       ArbolMvias=new ArbolMVias<>(); 
//                       ArbolMvias.setRaiz(nodoMvias);          
//                }else if (ArbolMvias==null){
//                     JTextField orden = new JTextField();
//       
//                Object[] message = {
//               "orden:", orden,
//         
//                };
//                orden.setText("4");
//                    JOptionPane.showConfirmDialog(null, message, "arbol Mvias se requiere el orden", JOptionPane.OK_CANCEL_OPTION);
//                     ArbolMvias=new ArbolMVias<>(Integer.parseInt(orden.getText()));
//                    
//                }
//        } catch (Exception e) {
//        }
       
         return ArbolMvias;
    }
        
        
        public IArbolBinario<String,String> prepararArbolB() {
          if (ArbolB==null){
              try {
                  JTextField orden = new JTextField();
                  Object[] message = {
                      "orden:", orden,
                      
                  };
                  orden.setText("3");
                  JOptionPane.showConfirmDialog(null, message, "arbol B se requiere el orden", JOptionPane.OK_CANCEL_OPTION);
                  ArbolB=new ArbolB<>(Integer.parseInt(orden.getText()));
                  
                  
                  
                  ArbolB.insertar("hola".toUpperCase(), "expresión usada para saludar informalmente ");
                  ArbolB.insertar("abeja".toUpperCase(), "insecto que da miel");
                  ArbolB.insertar("silla".toUpperCase(), "objeto para sentarse");
                  ArbolB.insertar("luz".toUpperCase(), "aparato que se usa para alumbrar");
                  
              } catch (ExceptionOrdenInvalida ex) {
                  Logger.getLogger(MainArboles.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
//        FileInputStream file =null;
//        File f;
//        try {
//             NodoMvias nodoB;
//            f = new File("b.obj");
//            if (ArbolB==null && f.isFile() && f.canRead()) {
//                   file=  new FileInputStream(f);
//                   ObjectInputStream tuberia=new ObjectInputStream(file);
//                   nodoB= (NodoMvias)tuberia.readObject();
//                   ArbolB=new ArbolB<>();
//                   ArbolB.setRaiz(nodoB);          
//            }else if (ArbolB==null){
//                JTextField orden = new JTextField();
//                Object[] message = {
//               "orden:", orden,
//         
//                };
//                orden.setText("3");
//                 JOptionPane.showConfirmDialog(null, message, "arbol B se requiere el orden", JOptionPane.OK_CANCEL_OPTION);
//                 ArbolB=new ArbolB<>(Integer.parseInt(orden.getText()));
//                 
//            }
//        } catch (Exception e) {
//        }
       
         return ArbolB;
    }
        
          /* procedimiento que me permite guardar los cambios de todos los arboles 
    */ 
    public void guardarArbol(String obj,IArbolBinario arbol){
        FileOutputStream file=null;
        try {      
           ArbolDeBusqueda=arbol;
        file=new FileOutputStream(obj);

            ObjectOutputStream tuberia = new ObjectOutputStream(file);
            tuberia.writeObject(ArbolDeBusqueda.getRaiz());
        } catch (FileNotFoundException e) {
            e.printStackTrace();    
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                file.close();
             //   JOptionPane.showMessageDialog(null, "Save Success");
            } catch (IOException e) {
               e.printStackTrace();
            }
            
            
        }
    }
    
    public void construirArbol() throws ExceptionOrdenInvalida{
        
              ArbolBinario=new ArbolBusquedaBinaria<>();
              ArbolBinario.insertar("hola", "cristian soza");
              ArbolBinario.insertar("abeja", "julio gonzales");
              ArbolBinario.insertar("silla", "llanos");
              ArbolBinario.insertar("test", "mario");
              ArbolBinario.insertar("sofa", "polo");
              ArbolBinario.insertar("caja", "sandra");
              ArbolBinario.insertar("Luz", "maria");
              ArbolBinario.insertar("silicona", "nicol");
     
              ArbolAvl=new ArbolAVL<>();
              ArbolAvl.insertar("hola", "cristian soza");
              ArbolAvl.insertar("abeja", "julio gonzales");
              ArbolAvl.insertar("silla", "llanos");
              ArbolAvl.insertar("test", "mario");
              ArbolAvl.insertar("sofa", "polo");
              ArbolAvl.insertar("caja", "sandra");
              ArbolAvl.insertar("Luz", "maria");
              ArbolAvl.insertar("silicona", "nicol");
   
         
              ArbolMvias=new ArbolMVias<>(4);
              ArbolMvias.insertar("hola", "cristian soza");
              ArbolMvias.insertar("abeja", "julio gonzales");
              ArbolMvias.insertar("silla", "llanos");
              ArbolMvias.insertar("test", "mario");
              ArbolMvias.insertar("sofa", "polo");
              ArbolMvias.insertar("caja", "sandra");
              ArbolMvias.insertar("Luz", "maria");
              ArbolMvias.insertar("silicona", "nicol");
            
              ArbolB=new ArbolB<String, String>(3);
            
              ArbolB.insertar("hola", "cristian soza");
              ArbolB.insertar("abeja", "julio gonzales");
              ArbolB.insertar("silla", "llanos");
              ArbolB.insertar("test", "mario");
              ArbolB.insertar("sofa", "polo");
              ArbolB.insertar("caja", "sandra");
              ArbolB.insertar("Luz", "maria");
              ArbolB.insertar("silicona", "nicol");
           
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        textField1 = new java.awt.TextField();
        textField2 = new java.awt.TextField();
        button3 = new java.awt.Button();
        button4 = new java.awt.Button();
        button5 = new java.awt.Button();
        jComboBox1 = new javax.swing.JComboBox<>();
        button6 = new java.awt.Button();
        textArea1 = new java.awt.TextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DICCIONARIO");
        jLabel1.setToolTipText("");

        textField1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        textField1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        textField2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        textField2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        textField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField2ActionPerformed(evt);
            }
        });

        button3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        button3.setLabel("Agregar");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        button4.setLabel("Buscar");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        button5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        button5.setLabel("Eliminar");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--select--", "Binario", "Avl", "Mvias", "B" }));

        button6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        button6.setLabel("mostrar");
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Palabra");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Significado");

        jMenu4.setText("File");

        jMenuItem3.setText("Guardar Todo");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(693, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addComponent(button6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(106, 106, 106)
                                        .addComponent(jLabel3)
                                        .addGap(28, 28, 28)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(164, 164, 164)
                        .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
         // Agregar En Cualquier arbol
            // TODO add your handling code here:
        prepararArbolBinario();
        prepararArbolAvl();
        prepararArbolMvias();
        prepararArbolB();
        
        JTextField key = new JTextField();
        JTextField value = new JTextField();
                Object[] message = {
            "Palabra:", key,
            "Significado:", value
        };
     
        int option = JOptionPane.showConfirmDialog(null, message, "Datos", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (!key.getText().equals("") && !value.getText().equals("")) {
                try {
                     ArbolBinario.insertar(key.getText().toUpperCase(), value.getText());
                     ArbolAvl.insertar(key.getText().toUpperCase(), value.getText());
                     ArbolMvias.insertar(key.getText().toUpperCase(), value.getText());
                    ArbolB.insertar(key.getText().toUpperCase(), value.getText());
                } catch (Exception e) {
                     System.out.println(e);
                }


            } else {
                JOptionPane.showMessageDialog(null,"Porfavor introduzca datos validos");
            }
        } else {
            System.out.println("canceled");
        }

    }//GEN-LAST:event_button3ActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
          String val = ArbolDeBusqueda.buscar(textField1.getText().toUpperCase());
          
        if(val!=null){
           textField2.setText(val);  
           
        }else{
             JOptionPane.showMessageDialog(null, "la palabra no fue Encontrada");
        }  
    }//GEN-LAST:event_button4ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        // Eliminar clave de cualquier arbol
       
        prepararArbolBinario();
        prepararArbolAvl();
        prepararArbolMvias();
        prepararArbolB();
        JTextField key = new JTextField();
        Object[] message = {
            "palabra a eliminar:", key,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Eliminar Palabra", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
         
            if (!key.getText().equals("")) {
               
              String val;
              val = ArbolDeBusqueda.buscar(key.getText().toUpperCase());
              if(val!= null){
                  try {
                       ArbolBinario.eliminar(key.getText().toUpperCase()); 
                   }  catch (Exception e) {
                    System.out.println(e);
                   }
                   
                   try {
                       ArbolAvl.eliminar(key.getText().toUpperCase());
                   }  catch (Exception e) {
                    System.out.println(e);
                   }
                   
                   try {
                        ArbolMvias.eliminar(key.getText().toUpperCase());
                   }  catch (Exception e) {
                    System.out.println(e);
                   }
                   
                   try{
                      ArbolB.eliminar(key.getText().toUpperCase());
                   }catch (Exception e) {
                    System.out.println(e);
                   }

                     JOptionPane.showMessageDialog(null,"Palabra Eliminada"); 
                    } else {
                    JOptionPane.showMessageDialog(null,"La Palabra No Esta En El Arbol");
                 }
               
                
            } else {
                JOptionPane.showMessageDialog(null,"Porfavor introduzca datos validos");
            }
        
        }
    }//GEN-LAST:event_button5ActionPerformed

    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        //mostrar Arboles
      textArea1.setText("");
      textArea1.append(ArbolDeBusqueda.mostrarArbol());
    }//GEN-LAST:event_button6ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
         
        prepararArbolBinario();
        prepararArbolAvl();
        prepararArbolMvias();
        prepararArbolB();
        
        guardarArbol("binario.obj",ArbolBinario);
        guardarArbol("avl.obj",ArbolAvl);
        guardarArbol("m-vias.obj",ArbolMvias);
        guardarArbol("b.obj",ArbolB);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainArboles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainArboles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainArboles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainArboles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainArboles().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button3;
    private java.awt.Button button4;
    private java.awt.Button button5;
    private java.awt.Button button6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private java.awt.TextArea textArea1;
    private java.awt.TextField textField1;
    private java.awt.TextField textField2;
    // End of variables declaration//GEN-END:variables
}
