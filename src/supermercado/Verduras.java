package supermercado;
// @author susiy
public class Verduras extends Producto{
    //Atributos
    private double pesoKg;
    
    //constructor

    public Verduras() {
    }
    
    public Verduras(double pesoKg, double precioBase, String nombre, String marca, String categoria, int id, int stock, int cantidad) {
        super(precioBase, nombre, marca, categoria, id, stock, cantidad);
        this.pesoKg = pesoKg;
    }

    public Verduras(double pesoKg, String tipoUnidad) {
        this.pesoKg = pesoKg;
    }

    @Override
    public double calcularPrecioFinal() {
        //incremento por desperdicio
        double precioVerdura =  this.precioBase*1.4;
        return precioVerdura;
    }

    @Override
    public String toString() {
        return String.format("Verduras {peso: %f kg, precio: %.2f}", pesoKg,calcularPrecioFinal());
    }
}
