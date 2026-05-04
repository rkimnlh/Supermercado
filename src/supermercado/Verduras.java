package supermercado;
// @author susiy
public class Verduras extends Producto{
    //Atributos
    private double pesoKg;
    private String tipoUnidad;
    
    //constructor

    public Verduras() {
    }
    
    public Verduras(double pesoKg, String tipoUnidad, double precioBase, String nombre, String marca, String categoria, int id, int stock, int cantidad) {
        super(precioBase, nombre, marca, categoria, id, stock, cantidad);
        this.pesoKg = pesoKg;
        this.tipoUnidad = tipoUnidad;
    }

    public Verduras(double pesoKg, String tipoUnidad) {
        this.pesoKg = pesoKg;
        this.tipoUnidad = tipoUnidad;
    }

    @Override
    public double calcularPrecioFinal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
