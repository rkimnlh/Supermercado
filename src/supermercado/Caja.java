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
            String mensajePromo = "";
            try {
                Mensaje promo = (Mensaje) item;//tratamos el obejto item como si fuera de tipo Mensaje y no Producto
                mensajePromo = promo.mostrarDetalle();
            } catch (ClassCastException e) {
                //si la interfaz no se implementa en la clase el caasting falla, pero con el catch lo ignoramos y continua la ejecucion 
            }
            double precioFinal = item.calcularPrecioFinal();
            double totalItem = item.getCantidad() * precioFinal;
            this.subtotal += totalItem;
            // Agregamos el mensaje al acumulador
            acumulador += String.format("%-6d %-15.15S %-10.2f %-10.2f\n",
                    item.getCantidad(), item.getNombre(), precioFinal, totalItem);

            if (!mensajePromo.equals("")) {//si mensajepromo no esta vacio 
                acumulador += "       " + mensajePromo + "\n"; //mostramos el mensaje especial
            }
        }
         
        this.iva = this.subtotal * 0.16;
        this.total = this.subtotal + this.iva;

        return acumulador;
    }
    
    @Override
    public String toString() {
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