package supermercado;

public class Fritura extends Producto implements Mensaje{
    private static final long serialVersionUID = 1L;
    
    protected String tipoFritura; 
    protected String sabor; 
    protected double contenidoGramos; 
    
    //constructores
    public Fritura(){
        
    }
    
    public Fritura(String tipoFritura, String sabor, double contenidoGramos) {
        this.tipoFritura = tipoFritura;
        this.sabor = sabor;
        this.contenidoGramos = contenidoGramos;
    }
    
    public Fritura(String tipoFritura, String sabor, double contenidoGramos, double precioBase, String nombre, String marca, String categoria, String id, int stock, int cantidad) {
        super(precioBase, nombre, marca, categoria, id, stock, cantidad);
        this.tipoFritura = tipoFritura;
        this.sabor = sabor;
        this.contenidoGramos = contenidoGramos;
    }
    
    @Override 
    public double calcularPrecioFinal(){ 
        double ieps = this.precioBase * 0.08; 
        
        return this.precioBase + ieps; 
    }
    
   

    @Override
    public String toString() {
        return  super.toString()+"Fritura{" + "tipoFritura=" + tipoFritura + ", sabor=" + sabor + ", contenidoGramos=" + contenidoGramos + '}';
    }
    
    @Override
    public String mostrarDetalle() {
        return ">>> Coma saludable.";
    }
}
