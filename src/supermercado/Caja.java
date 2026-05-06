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
        String acumulador = ""; 
        this.subtotal = 0.0; 

        for (Producto item : carrito) {
            String mensajePromo = ""; // aqui guardamos el return

            try {
                // realizamos el cast
                Mensaje promo = (Mensaje) item;

                // aqui utilizamos la interfaz 
                mensajePromo = promo.mostrarDetalle();

            } catch (ClassCastException e) {
                // Si no muestra nada, el mensaje se queda vacío ""
            }

                    // Al calcular el precio final, el polimorfismo sigue funcionando
            double precioFinal = item.calcularPrecioFinal();
            double totalItem = item.getCantidad() * precioFinal;
            this.subtotal += totalItem;

            // Agregamos el mensaje al acumulador si es que existe
            acumulador += String.format("%-6d %-15.15s %-10.2f %-10.2f\n",
                    item.getCantidad(), item.getNombre().toUpperCase(), precioFinal, totalItem);

            if (!mensajePromo.equals("")) {
                acumulador += "       " + mensajePromo + "\n"; // Se imprime debajo del producto
            }
        }
            

            
        

        // Calculamos el resto de los valores
        this.iva = this.subtotal * 0.16;
        this.total = this.subtotal + this.iva;

        return acumulador;
    }
    
    @Override
    public String toString() {
        // Calculamos y obtenemos el texto de los productos
        String listaProductos = obtenerListaYCalcular();

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