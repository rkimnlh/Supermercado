package supermercado;
//@author starmoon 
public class  CarneFria extends Producto implements Mensaje{
    private static final long serialVersionUID = 1L;
    protected double pesoKg;
    protected String tipoCarne;
    
    //Constructores
    public CarneFria (){
    }
    
    public CarneFria(double pesoKg, String tipoCarne){
        this.pesoKg = pesoKg;
        this.tipoCarne = tipoCarne;
    }
    
    public CarneFria(double precioBase, String nombre, String marca, String categoria, String id, int stock, int cantidad,double pesoKg, String tipoCarne){
        super(precioBase,nombre,marca,categoria,id,stock, cantidad);
        this.pesoKg = pesoKg;
        this.tipoCarne = tipoCarne;
    }
    
    //utilizamos polimorfismo definiendo el metodod calcularPrecioFinal especificamente para las carnes frias 
    @Override
    public double calcularPrecioFinal(){
        double ieps=pesoKg*this.precioBase;//multiplicamos los kilogramos de carne por el precio individual 
        ieps *= 1.08;//agregamos el ieps (Impuesto Especial sobre Produccion y Servicios )
        return ieps;
    }
    
    
    
   @Override
    public String toString() {
        // mantiene la tabla alineada mostrando los detalles extra que utilizaremos al mostrar el inventario 
        return String.format("%s | Carne: %s | Peso: %.2f Kg", 
                             super.toString(), this.tipoCarne, this.pesoKg);
    }
    
    //definimos el metodo abstracto de la interfaz 
    @Override
    public String mostrarDetalle() {
        return ">>> Mantengase en refrigeracion.";
    }
    
}
