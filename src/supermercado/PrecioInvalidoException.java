package supermercado;
//@author starmoon 
// heredamos de la clase Exception 
public class PrecioInvalidoException extends Exception {
    // constructor que cacha el texto de error cuando intentan poner un precio negativo o en ceros
    public PrecioInvalidoException(String mensaje) { 
        // con la palabra super() mandamos llamar al constructor de la clase padre y le pasamos el mensaje para que ella lo administre y lo imprima en el catch
        super(mensaje); 
    }
}