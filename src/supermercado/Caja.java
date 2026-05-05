package supermercado;
import java.util.ArrayList;

public class Caja {
    protected ArrayList<Producto> carrito;
    protected double subtotal;
    protected double iva;
    protected double total;

    public Caja() {
        this.carrito = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        this.carrito.add(p);
    }

    private String obtenerListaYCalcular() {
        String acumulador = ""; // Usamos un String simple en lugar de StringBuilder
        this.subtotal = 0.0; 

        for (Producto item : carrito) {
            double precioFinal = item.calcularPrecioFinal();
            double totalItem = item.getCantidad() * precioFinal;
            
            // Sumamos el valor a la variable de la clase
            this.subtotal += totalItem;

            // Vamos pegando cada línea de producto una tras otra
            acumulador += String.format("%-6d %-15.15s %-10.2f %-10.2f\n",
                    item.getCantidad(),
                    item.getNombre().toUpperCase(),
                    precioFinal,
                    totalItem);
        }

        // Calculamos el resto de los valores
        this.iva = this.subtotal * 0.16;
        this.total = this.subtotal + this.iva;

        return acumulador;
    }
    
    @Override
    public String toString() {
        // 1. Calculamos y obtenemos el texto de los productos
        String listaProductos = obtenerListaYCalcular();

        // 2. El String.format con el diseño del ticket
        return String.format("""
            ============================================
                            TIENDA
            ============================================
            CANT   PRODUCTO        PRECIO     TOTAL
            --------------------------------------------
            %s
            --------------------------------------------
                                  SUBTOTAL:      %.2f
                                 IVA (16%%):      %.2f
                                            ------------
                                     TOTAL: $    %.2f
            ============================================
            """, 
            listaProductos, this.subtotal, this.iva, this.total
        );
    }


}