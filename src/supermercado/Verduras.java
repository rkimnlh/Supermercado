package supermercado;
// @author susiy
public class Verduras extends Producto implements Promocionable{
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
        double precioVerdura= pesoKg*this.precioBase;
        //incremento por desperdicio
         precioVerdura *=1.4;
        return precioVerdura;
    }

    @Override
    public String toString() {
        return super.toString()+ "Verduras{" + "pesoKg=" + pesoKg + '}';
    }
    
    @Override
    public String mostrarDetalle() {
        return ">>> Mantenga a temperatura ambiente."; 
    }

    
}
