package supermercado;

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
    
    public CarneFria(double precioBase, String nombre, String marca, String categoria, int id, int stock, int cantidad,double pesoKg, String tipoCarne){
        super(precioBase,nombre,marca,categoria,id,stock, cantidad);
        this.pesoKg = pesoKg;
        this.tipoCarne = tipoCarne;
    }
    
    @Override
    public double calcularPrecioFinal(){
        double ieps=pesoKg*this.precioBase;
        ieps *= 1.08; 
        return ieps;
    }
    
    
    
    @Override
    public String toString() {
        return super.toString()+"Carne Fria{" + "pesoKg=" + pesoKg + ", tipoCarne=" + tipoCarne + '}';
    }
    
    @Override
    public String mostrarDetalle() {
        return ">>> Mantengase en refrigeracion.";
    }
    
}
