package supermercado;
//@author starmoon 
// con extends heredamos de la clase padre Exception
public class StockInvalidoException extends Exception {
    // constructor que recibe el texto de advertencia cuando nos quieren meter un stock inicial negativo
    public StockInvalidoException(String mensaje) { 
        // con la palabra super() mandamos llamar al constructor de la clase padre y le pasamos el mensaje para que ella lo administre y lo imprima en el catch
        super(mensaje); 
    }
}