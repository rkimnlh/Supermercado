package supermercado;

public class Cereal extends Producto implements Mensaje{
    private double contenidoGramos;
    private String tipo;
    
    //constructor vacio
    public Cereal(){
        
    }
    public Cereal(double contenidoGramos, String tipo){
        this.contenidoGramos = contenidoGramos;
        this.tipo = tipo;
    }
    public Cereal(double precioBase, String nombre, String marca, String categoria, int id, int stock, int cantidad, double contenidoGramos, String tipo){
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
        return super.toString()+"Cereal{" + "contenidoGramos=" + contenidoGramos + ", tipo=" + tipo + '}';
    }
    
    @Override
    public String mostrarDetalle() {
       return ">>> Mantenga cerrado el paquete.";
    }
    
}