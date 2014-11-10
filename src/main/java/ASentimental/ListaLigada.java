package ASentimental;
public class ListaLigada{
    private Nodo head; //cabeza de la lista
    
    private int numElems; 
    
    public ListaLigada(){
        create();        
    }
    
    public void create(){
        numElems=0;
        head=null;
    }    
    
    public boolean isEmpty(){
        return (numElems==0);
    }
    
    /**
     *regresa el tamaño de la lista (el numero de elementos que estan almacenados)
     */
    public int size(){
        return numElems;
    }
    
    /**
     *elimina todos los elementos de la lista
     */
    public void removeAll(){
        numElems=0;
        head=null;
    }               
    
    /**
     *devuelve el nodo en la posicion i
     */
    private Nodo find(int i){
        Nodo actual=head;
        int paso=1;
        while(paso<i){
            actual=actual.daSiguiente();
            paso++;
        }
        return actual;
    }
    
    /**
     *Da elobjeto que está dentro del nodo en la posici&oacute;n i
     */
    public Object get(int i) throws IndexOutOfBoundsException{
        if(numElems==0)
            throw new IndexOutOfBoundsException("Indice invalido. Lista vacia");        
        if(i>=1 && i<=numElems){
            Nodo actual=find(i);
            return actual.daElemento();            
        }
        else{
            throw new IndexOutOfBoundsException("Indice invalido");
        }
    }    

    /**
     *Agrega un objeto en la lista en la posición indicada
     */
    public void add(int i,Object obj) throws IndexOutOfBoundsException{
        if(i>=1 && i<=numElems+1){
            if(i==1){
                Nodo nuevo=new Nodo(obj,head);
	        head=nuevo;
            }
            else{
		Nodo previo=find(i-1);//nodo previo
		//
		Nodo nuevo=new Nodo(obj,previo.daSiguiente());//la referencia es al siguiente nodo
		previo.cambiaSiguiente(nuevo);   
	    }
            numElems++;
        }
        else{
            throw new IndexOutOfBoundsException("Indice invalido");
        }	
    }//add
    
    /**
     *Elimina el nodo que esta en la posicion indicada
     *@param i, un indice de la lista
     */
    
    public void remove(int i) throws IndexOutOfBoundsException{
        if(numElems==0)
            throw new IndexOutOfBoundsException("Indice invalido. Lista vacia");                
        if(i>=1 && i<=numElems){
            if(i==1){
                head=head.daSiguiente();
	    }
	    else{
                Nodo previo=find(i-1);
                Nodo actual=previo.daSiguiente();
		previo.cambiaSiguiente(actual.daSiguiente());
	    }
	    numElems--;
        }
        else{
	    throw new IndexOutOfBoundsException("Indice invalido");
        }
    }

    
   
}//fin de clase
