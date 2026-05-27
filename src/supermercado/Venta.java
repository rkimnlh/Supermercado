package supermercado;
import java.io.Serializable;

public class Venta implements Serializable {
    private String nombreProducto;
    private int cantidadVendida;
    private double importeTotal;

    public Venta(String nombreProducto, int cantidadVendida, double importeTotal) {
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.importeTotal = importeTotal;
    }

   @Override
    public String toString() {
        return String.format("Producto: %-28.28s | Cantidad: %-4d | Total: $%8.2f", 
                             this.nombreProducto, this.cantidadVendida, this.importeTotal);
    }
}
