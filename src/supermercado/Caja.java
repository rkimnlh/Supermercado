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
            // 1. Por defecto, asumimos que no hay descuento (0.0)
            double descuentoUnitario = 0.0;

            // 2. Preguntamos SI este 'item' en específico implementa tu interfaz.
            // OJO: Cambia la palabra "Descontable" por el nombre exacto que le pusiste a tu interfaz
            if (item instanceof Promocionable) {

                // 3. Si sí la implementa, "transformamos" temporalmente el item a esa interfaz
                // para que Java nos deje usar el método aplicarDescuento()
                Promocionable itemConDescuento = (Promocionable) item;
                descuentoUnitario = itemConDescuento.aplicarDescuento();
            }
            double totalItemSinDescuento = item.getCantidad() * item.getPrecioBase();
            // Continuamos con el cálculo que ya tenías
            double totalDescuento = descuentoUnitario * item.getCantidad();
            
            // 3. Calculamos el total real a pagar y lo sumamos a la caja
            double totalItemFinal = totalItemSinDescuento - totalDescuento;
            this.subtotal += totalItemFinal;

            // 4. Imprimimos la línea normal del producto (como en la imagen original)
            acumulador += String.format("%-6d %-15.15s %-10.2f %-10.2f\n",
                    item.getCantidad(),
                    item.getNombre().toUpperCase(),
                    item.getPrecioBase(),
                    totalItemSinDescuento);

            // 5. Si hubo descuento, agregamos la línea extra tipo ticket
            if (descuentoUnitario > 0) {
                // Formateamos el descuento con un signo negativo
                String textoDescuento = String.format("-%.2f", totalDescuento);
                
                // Dejamos el espacio de "CANT" vacío, ponemos el nombre del descuento,
                // dejamos "PRECIO" vacío y alineamos el descuento en la columna "TOTAL"
                acumulador += String.format("%-6s %-15.15s %-10s %-10s\n",
                        "", "DESC VOLUMEN", "", textoDescuento);
            }
            double precioFinal = item.calcularPrecioFinal();
            

            
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