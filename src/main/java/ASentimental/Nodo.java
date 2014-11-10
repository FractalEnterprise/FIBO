package ASentimental;
public class Nodo {
    private Object elemento;
    private Nodo siguiente;
    public Nodo(Object e){
        elemento=e;
        siguiente=null;
    }
    public Nodo(Object e,Nodo sig){
        elemento=e;
        siguiente=sig;
    }
    public void cambiaElemento(Object nvo){
        elemento=nvo;
    }
    public Object daElemento(){
	return elemento;
    }
    public void cambiaSiguiente(Nodo sig){
	siguiente=sig;
    }
    public Nodo daSiguiente(){
	return siguiente;
    }
}



