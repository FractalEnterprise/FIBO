package ASentimental;
/**
 *Esta clase se encarga de recibir y clasificar una serie de cadenas a trav&eacute;s de comparaci&oacute;nes de las incidencias de estas contra
 * cadenas (palabras) alojadas en diccionarios (diccionario de palabras buenas y diccionario de palabras malas)
 *El proceso para clasificar:
 *Primero encuentra y elimina simbolos no alfanum&eacute;ricos, convierte toda la cadena a minusculas y elimina los acentos.
 *Se divide la cadena en subcadenas
 *Cuando una subcadena pertenece a un diccionario, se verifica que sea precedida por la subcadena "no", si ese es el caso entonces la negaci&oacute;n de la subcadena pertenece al diccionario opuesto
 *Al final se comparan las incidencias en los diccionarios
 *
 *El algoritmo regresa
 *1 si hay m&aacute;s cadenas en el diccionario diccB (comentario bueno)
 *-1 si hay m&aacute;s cadenas en el diccionario diccM (comentario malo)
 * 0 si hay el mismo n&uacute;mero de cadenas en ambos diccionarios (comentario neutro)
 * Para modelar un diccionario se utiliza el objeto del tipo ListaLiagada
 * @author Diana Luc&iacute;a Guerrero Ch&aacute;vez
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.unam.fciencias.dao.ComentarioDAO;
import mx.unam.fciencias.model.dto.ComentarioDto; //paquete donde se encuentra la clase para modificar la tabla "comentarioMateria"
import mx.unam.fciencias.service.AlumnoService;

@ManagedBean
@SessionScoped
public class AnalisisSentimental {
    ListaLigada diccionario = new ListaLigada();//creamos un diccionario 
    ListaLigada diccB = new ListaLigada();//creamos un diccionario vac&iacute;o de palabras buenas
    ListaLigada diccM = new ListaLigada();//creamos un diccionario vac&iacute;o de palabras malas
    int contadorB;//numero de palabras que se encuentran en el diccionario de palabras buenas
    int contadorM;//numero de palabras que se encuentran en el diccionario de palabras malas
    int popularidad;//un contador que representa la popularidad de la materia o del profesor.
    public ComentarioDto comentarioMateria;
    public ComentarioDAO comentarioDAO;
    public List<ComentarioDto> listaComentarios;//una lista que guardará todos los comentarios
    private AlumnoService alumnoService = new AlumnoService();
    
    @PostConstruct
    public void init(){
        /**
        comentarioMateria = new ComentarioDto();
        comentarioDAO = new ComentarioDAO();
        
        */
        comentarioDAO = new ComentarioDAO();
        listaComentarios = new ArrayList<ComentarioDto>();
        listaComentarios.addAll(comentarioDAO.selectAllComments()); //poblamos la lista con los comentarios
        comentarioMateria = new ComentarioDto();
        popularidad=0;
       }
    
    
      
    public void realizarAnalisis(){
        crearDiccionarios();
        listaComentarios = comentarioDAO.selectAllComments();
        /**
        for(int i=0; i< listaComentarios.size();i++){
            int popularidad = obtenerSubcadenas(comentarioDAO.findByNumber(i).getComentario());
            
        }
        * */
        for(int i=2; i< listaComentarios.size()+2;i++){
             setPopularidad(obtenerSubcadenas(comentarioDAO.findByNumber(i).getComentario()));
            //System.out.println("popularidad "+getPopularidad());
        }
      }
    
    /**
     * Obtiene y guarda en una lista, las subcadenas (palabras) de una cadena 'p' que recibe como par&aacute;metro 
     *Antes de realizar todo esto, crea los diccionarios de palabras buenas y malas 
     
     * @param c
     */
    public int obtenerSubcadenas(String c){
                    
        String palabras[] = c.split(" ");
	return eliminarSimbolos(palabras); 
    }
    
    /**
     *
    Busca y elimina los simbolos diferentes a numero y letras de una cadena, las cadenas resultantes se almacenar&aacute;n en la lista 'p' que recibi&oacute; como par&aacute;metro.
    *Despu&eacute;s elimina los acentos y cambia las letras a minusculas.
    *Al final, poblamos un Diccionario con estas cadenas
    *@param p es un arreglo que almacena cadenas
    */
    public int eliminarSimbolos(String[] p){
       
	//A continuaci&oacute;n recorremos la lista que recibimos como par&aacute;metro y sustraemos cada uno de sus elementos
        for(int i=0; i < p.length; i++){
	    String pal="";
	    String nuevaPal="";   
	    pal = p[i];
	    pal = pal.toLowerCase();//convertimos todos los caracteres a minusculas
	    //eliminamos los acentos
	    pal = pal.replace ("á","a");
	    pal = pal.replace ("é","e");
	    pal = pal.replace ("í","i");
	    pal = pal.replace ("ó","o");
	    pal = pal.replace ("ú","u");
	    
            //A continuaci&oacute;n recorremos la cadena en busca de caracteres no permitidos
            for(int j=0; j< pal.length();j++){
                char c = pal.charAt(j); 
                if ( Character.isDigit(c) || Character.isLetter(c) ){ //Si el caracter actual es un numero o letra lo concatena a una nueva cadena
		    //concatena a una nueva cadena
		    nuevaPal += c;
		    p[i]=nuevaPal;//reemplazamos nuestra nueva cadena en el arreglo original
                }
	    }//for
	    diccionario.add(1,p[i]); //agregamos nuestra palabra con caracteres permitidos al diccionario
	}//for
        
        return(analizar(diccionario));
        
    }//eliminarSimbolos
    
    /**
     *compara el numero de cadenas (en una lista que recibe como par&aacute;metro) que se encuentran en los diccionarios de palabras buenas y malas
     *Una vez que se encuentre una cadena que pertenezca a uno de los dos diccionarios, se revisa si la cadena est&aacute; precedida por la cadena "no", Si esto sucede entonces encontramos la negaci&oacute;n de esa cadena. es decir que la cadena pertenece al diccionario opuesto y por lo tanto aumentamos en 2 unidades el contador de dicho diccionario
Ejemplo: "malo" pertenece al diccionario diccM "no malo" pertenece al diccionario diccB y por lo tanto aumentamos en 2 el contador de diccB.
*Nota: aumentamos en 2 el contador, por que la subcadena "malo" tambi&eacute;n se toma en cuenta 

     * regresa 1 si hay m&aacute;s cadenas en el diccionario diccB (comentario bueno)
     * regresa -1 si hay m&aacute;s cadenas en el diccionario diccM (comentario malo)
     * regresa 0 si hay el mismo n&uacute;mero de cadenas en ambos diccionarios (comentario neutro)
     */
    public int analizar(ListaLigada p){
	for(int i=1; i<p.size()+1; i++){
	    for(int j=1; j<diccB.size(); j++){//comparamos en el diccionario de palabras buenas la cadena actual (p.get(i))
		if(diccB.get(j).equals(p.get(i))){
		    if(i<p.size() && p.get(i+1).equals("no")){//encontramos la negacion de una palabra
			contadorM+=2;//el puntaje corresponde al diccionario contrario
		    }
		    //p.remove(i);
                    contadorB++;
		}//if
	    }//for
            
	    for(int j=1; j<diccM.size(); j++){
		if(diccM.get(j).equals(p.get(i)) ){//comparamos en el diccionario de palabras malas la cadena actual (p.get(i))
		    if(i<p.size() && p.get(i+1).equals("no")){//encontramos la negacion de una palabra
			contadorB+=2;//el puntaje corresponde al diccionario contrario
		    }
		    contadorM++;
		    //p.remove(i);
		}  
	    }//for
	    
	}//for
	
        
        //comparamos los contadores:
        if(contadorB > contadorM){
            return 1;//es un comentario bueno
        }else{
            if(contadorM > contadorB)
		return -1;//es un comentario malo
        }
        return 0; //es un comentario neutro
	
    }//analizar
    
    /**
     * Suma a una variable establecida un entero que representa la popularidad de un profesor o de una materia
     * @param p 
     */
    public void setPopularidad(int p){
        this.popularidad += p;
    }
    
    /**
     * 
     * @return un entero que representa la popularidad de un profesor o de una materia
     */
    public int getPopularidad(){
        return this.popularidad;
    }
    
    
     /**
     * se declaran las palabras para cada diccionario
     */
    public void crearDiccionarios(){
   
diccB.add(1,"vivaracho");
diccB.add(1,"vivaracha");
diccB.add(1,"versado");
diccB.add(1,"vendedor");
diccB.add(1,"tranquilo");
diccB.add(1,"tranquila");
diccB.add(1,"tolerante");
diccB.add(1,"tenaz");
diccB.add(1,"tacto");
diccB.add(1,"sutil");
diccB.add(1,"suave");
diccB.add(1,"sosegado");
diccB.add(1,"sosegada");
diccB.add(1,"solucionador");
diccB.add(1,"solidario");
diccB.add(1,"solidaria");
diccB.add(1,"sobrio");
diccB.add(1,"sobria");
diccB.add(1,"sistematico");
diccB.add(1,"sincero");
diccB.add(1,"simpatico");
diccB.add(1,"simpatica");
diccB.add(1,"servicial");
diccB.add(1,"serio");
diccB.add(1,"seria");
diccB.add(1,"sereno");
diccB.add(1,"sensible");
diccB.add(1,"sensato");
diccB.add(1,"sensata");
diccB.add(1,"seguro");
diccB.add(1,"segura");
diccB.add(1,"sabio");
diccB.add(1,"sabia");
diccB.add(1,"risueño");
diccB.add(1,"risueña");
diccB.add(1,"resuelto");
diccB.add(1,"responsable");
diccB.add(1,"respetuoso");
diccB.add(1,"resistente");
diccB.add(1,"reflexivo");
diccB.add(1,"refinadao");
diccB.add(1,"refinada");
diccB.add(1,"recursos");
diccB.add(1,"recto");
diccB.add(1,"recomendable");
diccB.add(1,"realista");
diccB.add(1,"razonable");
diccB.add(1,"rapido");
diccB.add(1,"radiante");
diccB.add(1,"puntual");
diccB.add(1,"prudente");
diccB.add(1,"protectora");
diccB.add(1,"protector");
diccB.add(1,"profundo");
diccB.add(1,"profunda");
diccB.add(1,"productivo");
diccB.add(1,"previsora");
diccB.add(1,"previsor");
diccB.add(1,"prevenido");
diccB.add(1,"prevenida");
diccB.add(1,"preparado");
diccB.add(1,"preparada");
diccB.add(1,"preciso");
diccB.add(1,"precisa");
diccB.add(1,"precavido");
diccB.add(1,"precavida");
diccB.add(1,"practico");
diccB.add(1,"practica");
diccB.add(1,"positivo");
diccB.add(1,"ponderado");
diccB.add(1,"ponderada");
diccB.add(1,"polivalente");
diccB.add(1,"poderoso");
diccB.add(1,"poderosa");
diccB.add(1,"persuasivo");
diccB.add(1,"persuasiva");
diccB.add(1,"persistente");
diccB.add(1,"perseverante");
diccB.add(1,"perceptivo");
diccB.add(1,"perceptiva");
diccB.add(1,"particular");
diccB.add(1,"pacifico");
diccB.add(1,"pacifica");
diccB.add(1,"paciente");
diccB.add(1,"osado");
diccB.add(1,"osada");
diccB.add(1,"original");
diccB.add(1,"orientado");
diccB.add(1,"organizado");
diccB.add(1,"organizada");
diccB.add(1,"ordenado");
diccB.add(1,"ordenada");
diccB.add(1,"optimista");
diccB.add(1,"oportuno");
diccB.add(1,"oportuna");
diccB.add(1,"objetivo");
diccB.add(1,"objetiva");
diccB.add(1,"notable");
diccB.add(1,"negociador");
diccB.add(1,"motivador ");
diccB.add(1,"moderado");
diccB.add(1,"moderada");
diccB.add(1,"minucioso ");
diccB.add(1,"minucioso");
diccB.add(1,"minuciosa");
diccB.add(1,"metodico");
diccB.add(1,"metodica");
diccB.add(1,"meticuloso");
diccB.add(1,"meticulosa");
diccB.add(1,"mejores");
diccB.add(1,"mejor");
diccB.add(1,"maravilloso");
diccB.add(1,"maravillosa");
diccB.add(1,"mañoso");
diccB.add(1,"magnanimo");
diccB.add(1,"magnanima");
diccB.add(1,"maduro");
diccB.add(1,"madura");
diccB.add(1,"luminoso");
diccB.add(1,"luminosa");
diccB.add(1,"lucido");
diccB.add(1,"lucida");
diccB.add(1,"logico");
diccB.add(1,"logica");
diccB.add(1,"lider");
diccB.add(1,"leal");
diccB.add(1,"laborioso");
diccB.add(1,"justo");
diccB.add(1,"justa");
diccB.add(1,"juicioso");
diccB.add(1,"juiciosa");
diccB.add(1,"inventivo");
diccB.add(1,"interesante");
diccB.add(1,"insolito");
diccB.add(1,"insolita");
diccB.add(1,"ingenioso");
diccB.add(1,"ingeniosa");
diccB.add(1,"indulgente");
diccB.add(1,"independiente");
diccB.add(1,"incansable");
diccB.add(1,"imaginativo");
diccB.add(1,"humorista");
diccB.add(1,"humanitario");
diccB.add(1,"humanitaria");
diccB.add(1,"humanista");
diccB.add(1,"honesto");
diccB.add(1,"habil");
diccB.add(1,"gracioso");
diccB.add(1,"graciosa");
diccB.add(1,"gerencial");
diccB.add(1,"genial");
diccB.add(1,"generoso");
diccB.add(1,"generosa");
diccB.add(1,"formal");
diccB.add(1,"flexible");
diccB.add(1,"firme");
diccB.add(1,"fiel");
diccB.add(1,"fiable");
diccB.add(1,"feliz");
diccB.add(1,"extravertido");
diccB.add(1,"extraordinario");
diccB.add(1,"extraordinaria");
diccB.add(1,"experto");
diccB.add(1,"exigente");
diccB.add(1,"excepcional");
diccB.add(1,"excelente");
diccB.add(1,"exacto");
diccB.add(1,"exacta");
diccB.add(1,"etico");
diccB.add(1,"etica");
diccB.add(1,"estupendo");
diccB.add(1,"estupenda");
diccB.add(1,"estudiosao");
diccB.add(1,"estudiosa");
diccB.add(1,"estilizado");
diccB.add(1,"estilizada");
diccB.add(1,"estable");
diccB.add(1,"especializado");
diccB.add(1,"especializada");
diccB.add(1,"esmerado");
diccB.add(1,"esmerada");
diccB.add(1,"esforzada");
diccB.add(1,"entusiasta");
diccB.add(1,"entusiasmado");
diccB.add(1,"entusiasmada");
diccB.add(1,"entretenido");
diccB.add(1,"entretenida");
diccB.add(1,"entregado");
diccB.add(1,"energico");
diccB.add(1,"energica");
diccB.add(1,"encantadora");
diccB.add(1,"encantador");
diccB.add(1,"emprendedora");
diccB.add(1,"emprendedor");
diccB.add(1,"empatico");
diccB.add(1,"empatica");
diccB.add(1,"elegante");
diccB.add(1,"ejecutivo");
diccB.add(1,"ejecutiva");
diccB.add(1,"efusivo");
diccB.add(1,"efusiva");
diccB.add(1,"eficiente");
diccB.add(1,"eficaz");
diccB.add(1,"educadao");
diccB.add(1,"educada");
diccB.add(1,"ecuanime");
diccB.add(1,"economico");
diccB.add(1,"dulce");
diccB.add(1,"distinguido");
diccB.add(1,"distinguida");
diccB.add(1,"dispuesto");
diccB.add(1,"dispuesta");
diccB.add(1,"discreto");
diccB.add(1,"discreta");
diccB.add(1,"diplomatico");
diccB.add(1,"diplomatica");
diccB.add(1,"dinamico");
diccB.add(1,"dinamica");
diccB.add(1,"diligente");
diccB.add(1,"dialogante");
diccB.add(1,"detallista");
diccB.add(1,"despierto");
diccB.add(1,"despierta");
diccB.add(1,"despejado");
diccB.add(1,"despejada");
diccB.add(1,"desenvuelto");
diccB.add(1,"desenvuelta");
diccB.add(1,"deliciosa");
diccB.add(1,"delicado");
diccB.add(1,"delicada");
diccB.add(1,"delegador");
diccB.add(1,"dedicado");
diccB.add(1,"dedicada");
diccB.add(1,"decidido");
diccB.add(1,"decidida");
diccB.add(1,"curioso");
diccB.add(1,"curiosa");
diccB.add(1,"cumplidora");
diccB.add(1,"cumplidor");
diccB.add(1,"culto");
diccB.add(1,"culta");
diccB.add(1,"cuidadoso");
diccB.add(1,"critico");
diccB.add(1,"creativo");
diccB.add(1,"creativa");
diccB.add(1,"cortes");
diccB.add(1,"cordial");
diccB.add(1,"coordinador");
diccB.add(1,"cooperativo");
diccB.add(1,"convincente");
diccB.add(1,"contento");
diccB.add(1,"contenta");
diccB.add(1,"constructivo");
diccB.add(1,"constante");
diccB.add(1,"consecuente");
diccB.add(1,"consciente");
diccB.add(1,"conocedora");
diccB.add(1,"conocedor");
diccB.add(1,"concreto");
diccB.add(1,"conciliador");
diccB.add(1,"concienzudo");
diccB.add(1,"concienzuda");
diccB.add(1,"comunicador");
diccB.add(1,"comprometido");
diccB.add(1,"comprometida");
diccB.add(1,"competente");
diccB.add(1,"compasivo");
diccB.add(1,"compasiva");
diccB.add(1,"colaborador");
diccB.add(1,"coherente");
diccB.add(1,"claro");
diccB.add(1,"chistoso");
diccB.add(1,"chistosa");
diccB.add(1,"chido");
diccB.add(1,"chida");
diccB.add(1,"cauto");
diccB.add(1,"caritativo");
diccB.add(1,"caritativa");
diccB.add(1,"cariñoso");
diccB.add(1,"cariñosa");
diccB.add(1,"caracteristico");
diccB.add(1,"caracteristica");
diccB.add(1,"caracter");
diccB.add(1,"capaz");
diccB.add(1,"bullicioso");
diccB.add(1,"bulliciosa");
diccB.add(1,"buenos");
diccB.add(1,"bueno");
diccB.add(1,"buena");
diccB.add(1,"buen");
diccB.add(1,"brillante");
diccB.add(1,"bonito");
diccB.add(1,"bonita");
diccB.add(1,"bondadoso");
diccB.add(1,"bondadosa");
diccB.add(1,"bien");
diccB.add(1,"bailarina");
diccB.add(1,"bailarin");
diccB.add(1,"autentico");
diccB.add(1,"autentica");
diccB.add(1,"atrevido");
diccB.add(1,"atrevida");
diccB.add(1,"atractivo");
diccB.add(1,"atractiva");
diccB.add(1,"atento");
diccB.add(1,"atenta");
diccB.add(1,"asombroso");
diccB.add(1,"asombrosa");
diccB.add(1,"asertivo");
diccB.add(1,"asertiva");
diccB.add(1,"apuesto");
diccB.add(1,"apuesta");
diccB.add(1,"apto");
diccB.add(1,"apta");
diccB.add(1,"aplicado");
diccB.add(1,"aplicada");
diccB.add(1,"animoso");
diccB.add(1,"animosa");
diccB.add(1,"analitico");
diccB.add(1,"amplio");
diccB.add(1,"amplia");
diccB.add(1,"ambicioso");
diccB.add(1,"ambiciosa");
diccB.add(1,"amable");
diccB.add(1,"alerta");
diccB.add(1,"alegre");
diccB.add(1,"agudo");
diccB.add(1,"aguda");
diccB.add(1,"agradable");
diccB.add(1,"agil");
diccB.add(1,"afectuoso");
diccB.add(1,"afectuosa");
diccB.add(1,"afable");
diccB.add(1,"adecuado");
diccB.add(1,"adecuada");
diccB.add(1,"adaptable");
diccB.add(1,"actual");
diccB.add(1,"activo");
diccB.add(1,"activa");
diccB.add(1,"acertado");
diccB.add(1,"acertada");
diccB.add(1,"accesible");
diccB.add(1,"abierto");
    
//diccionario de palabras malas
diccM.add(1,"tontas");
diccM.add(1,"tontos");
diccM.add(1,"tonta");
diccM.add(1,"tonto");
diccM.add(1,"maleducada");
diccM.add(1,"grosera");
diccM.add(1,"grosero");
diccM.add(1,"maleducado");
diccM.add(1,"neurotico");
diccM.add(1,"neurotica");
diccM.add(1,"insensible");
diccM.add(1,"arcaica");
diccM.add(1,"arcaico");
diccM.add(1,"ofensiva");
diccM.add(1,"ofensivo");
diccM.add(1,"sucia");
diccM.add(1,"sucio");
diccM.add(1,"borracha");
diccM.add(1,"borracho");
diccM.add(1,"desordenada");
diccM.add(1,"desordenado");
diccM.add(1,"loca");
diccM.add(1,"loco");
diccM.add(1,"mendiga");
diccM.add(1,"mendigo");
diccM.add(1,"agresivo ");
diccM.add(1,"violenta");
diccM.add(1,"violento");
diccM.add(1,"porqueria");
diccM.add(1,"chismosa");
diccM.add(1,"chismoso");
diccM.add(1,"presume");
diccM.add(1,"molesta");
diccM.add(1,"molesto");
diccM.add(1,"soberbia");
diccM.add(1,"soberbio");
diccM.add(1,"sonsa");
diccM.add(1,"sonso");
diccM.add(1,"ignorante");
diccM.add(1,"bastarda");
diccM.add(1,"bastardo");
diccM.add(1,"decadente");
diccM.add(1,"inepta");
diccM.add(1,"inepto");
diccM.add(1,"incompetente");
diccM.add(1,"vividora");
diccM.add(1,"vividor");
diccM.add(1,"embustera");
diccM.add(1,"embustero");
diccM.add(1,"cobarde");
diccM.add(1,"psicopata");
diccM.add(1,"traidora");
diccM.add(1,"traidor");
diccM.add(1,"presumidisima");
diccM.add(1,"presumidisimo");
diccM.add(1,"ridicula");
diccM.add(1,"ridiculo");
diccM.add(1,"quisquillosa");
diccM.add(1,"quisquilloso");
diccM.add(1,"apesta");
diccM.add(1,"deprimente");
diccM.add(1,"patetica");
diccM.add(1,"patetico");
diccM.add(1,"agresiva");
diccM.add(1,"agresivo");
diccM.add(1,"gritona");
diccM.add(1,"griton");
diccM.add(1,"enojona");
diccM.add(1,"enojon");
diccM.add(1,"babosa");
diccM.add(1,"baboso");
diccM.add(1,"lacra");
diccM.add(1,"mañosa");
diccM.add(1,"mañoso");
diccM.add(1,"goloso");
diccM.add(1,"corrientisimo");
diccM.add(1,"corriente");
diccM.add(1,"chafa");
diccM.add(1,"imbecil");
diccM.add(1,"cabrona");
diccM.add(1,"presumida");
diccM.add(1,"presumido");
diccM.add(1,"cabron");
diccM.add(1,"maldita");
diccM.add(1,"malos");
diccM.add(1,"maldito");
diccM.add(1,"pesima");
diccM.add(1,"pesimo");
diccM.add(1,"asco");
diccM.add(1,"aburridisima");
diccM.add(1,"aburridisimo");
diccM.add(1,"aburrida");
diccM.add(1,"aburrido");
diccM.add(1,"tediosa");
diccM.add(1,"tedioso");
diccM.add(1,"horribles");
diccM.add(1,"horrible");
diccM.add(1,"viciosa");
diccM.add(1,"vicioso");
diccM.add(1,"odiosa");
diccM.add(1,"odioso");
diccM.add(1,"latosa");
diccM.add(1,"latoso");
diccM.add(1,"envidiosa");
diccM.add(1,"envidioso");
diccM.add(1,"peor");
diccM.add(1,"mamona");
diccM.add(1,"mamon");
diccM.add(1,"adefesio");
diccM.add(1,"flojera");
diccM.add(1,"floja");
diccM.add(1,"flojo");
diccM.add(1,"malisimas");
diccM.add(1,"malisimos");
diccM.add(1,"malisima");
diccM.add(1,"malisimo");
diccM.add(1,"mala");
diccM.add(1,"malo");

    }//crearDiccionarios
    
    
 }
