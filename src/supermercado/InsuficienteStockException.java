package supermercado;
//@author starmoon 
// usamos extends para heredar de la clase padre Exception que ya viene en Java
public class InsuficienteStockException extends Exception {
    // este constructor recibe el texto de error cuando el stock pedido por el usuario es induficiente 
    public InsuficienteStockException(String mensaje) { 
        // usamos super() para pasarle ese texto al constructor de la clase padre 
        super(mensaje); 
    }
}