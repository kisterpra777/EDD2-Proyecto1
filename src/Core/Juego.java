/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

/**
 *
 * @author HOLA
 */
public class Juego {

    public Juego() {
    }
    
    public enum EnumEventos {
    JEFEENEMIGO,COFRES,PORTAL,TRAMPA
}
    
    public void ejecutarEvento(EnumEventos evento){
        switch(evento){
            case JEFEENEMIGO:
                jefeEnemigo();
            case COFRES:
                cofre();
            case PORTAL:
                abrirPortal();
            case TRAMPA:
                trampa();
                
        }
    }
    
    public void jefeEnemigo(int gemaPedir){
        
    }
    public void cofre(){
        
    }
    public void abrirPortal(){
        
    }
    public void trampa(){
        
    }
    
}
