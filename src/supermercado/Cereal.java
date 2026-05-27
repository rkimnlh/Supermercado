package supermercado;

public class Cereal extends Producto implements Mensaje{
    private static final long serialVersionUID = 1L;
    
    protected double contenidoGramos;
    protected String tipo;
    
    //constructor
    public Cereal(){
        
    }
    
    public Cereal(double contenidoGramos, String tipo){
        this.contenidoGramos = contenidoGramos;
        this.tipo = tipo;
    }
    
    public Cereal(double precioBase, String nombre, String marca, String categoria, String id, int stock, int cantidad, double contenidoGramos, String tipo){
        super(precioBase,nombre,marca,categoria,id,stock, cantidad);
        this.contenidoGramos = contenidoGramos;
        this.tipo = tipo;
    }
    
    @Override
    public double calcularPrecioFinal(){
        double ieps = this.precioBase * 0.08; 
        return precioBase + ieps;
    }

    @Override
    public String toString() {
        return String.format("%s | Cereal de %s | Contenido: %.0f g", 
                             super.toString(), this.tipo, this.contenidoGramos);
    }
    
    @Override
    public String mostrarDetalle() {
       return ">>> Mantenga cerrado el paquete.";
    }
    
}