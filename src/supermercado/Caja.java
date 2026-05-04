package supermercado;
import java.util.ArrayList;

public class Caja {
    
    protected ArrayList<Producto> carrito;
    
   
    protected double subtotal;
    protected double iva;
    protected double total;

   
    public Caja() {
        this.carrito = new ArrayList<>();
        this.subtotal = 0.0;
        this.iva = 0.0;
        this.total = 0.0;
    }

    public Caja(ArrayList<Producto> carrito, double subtotal, double iva, double total) {
        this.carrito = carrito;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
    }
    
    
    // Método para agregar los productos al ticket
    public void agregarProducto(Producto p) {
        this.carrito.add(p);
    }

    // Método polimórfico y con formato para imprimir el ticket
    public void generarTicket() {
        // Reiniciamos los valores por si se cobra más de un cliente
        this.subtotal = 0.0;
        
        System.out.println("\n============================================");
        System.out.println("                TIENDA");
        System.out.println("============================================");
        System.out.printf("%-6s %-15s %-10s %-10s\n", "CANT", "PRODUCTO", "PRECIO", "TOTAL");
        System.out.println("--------------------------------------------");

        // Recorremos el ArrayList (Aquí entra el polimorfismo si llamamos a métodos propios)
        for (Producto item : carrito) {
            
            double totalPorProducto = item.getCantidad() * item.calcularPrecioFinal();
            this.subtotal += totalPorProducto;

            System.out.printf("%-6d %-15.15s %-10.2f %-10.2f\n", 
                item.getCantidad(), 
                item.getNombre().toUpperCase(), 
                item.calcularPrecioFinal(), 
                totalPorProducto);
        }

        // Cálculos finales
        this.iva = this.subtotal * 0.16;
        this.total = this.subtotal + this.iva;

        System.out.println("--------------------------------------------");
        System.out.printf("%32s %10.2f\n", "SUBTOTAL:", this.subtotal);
        System.out.printf("%32s %10.2f\n", "IVA (16%):", this.iva);
        System.out.println("                                ------------");
        System.out.printf("%32s $ %9.2f\n", "TOTAL:", this.total);
        System.out.println("============================================\n");
    }
}