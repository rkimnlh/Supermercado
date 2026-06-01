package supermercado;
//author starmoon 
import java.io.Serializable;

public class Venta implements Serializable {
    private String nombreProducto;
    private int cantidadVendida;
    private double importeTotal;
    
    //Constructor
    public Venta(String nombreProducto, int cantidadVendida, double importeTotal) {
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.importeTotal = importeTotal;
    }
    // Mantiene los textos alineados en columnas usando para cuando consultemos el historial de ventas desde el menu opcion 4
   @Override
    public String toString() {
        return String.format("Producto: %-28.28s | Cantidad: %-4d | Total: $%8.2f", 
                             this.nombreProducto, this.cantidadVendida, this.importeTotal);
    }
}
