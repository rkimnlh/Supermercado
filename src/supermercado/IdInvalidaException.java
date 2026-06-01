package supermercado;
//@author starmoon 
// usamos extends para heredar de Exception. 
public class IdInvalidaException extends Exception {
    // este constructor atrapa el texto personalizado que le mandamos desde el validador
    public IdInvalidaException(String mensaje) { 
        // con la palabra super() mandamos llamar al constructor de la clase padre y le pasamos el mensaje para que ella lo administre y lo imprima en el catch
        super(mensaje); 
    }
}