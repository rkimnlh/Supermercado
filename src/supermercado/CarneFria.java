package supermercado;

public class  CarneFria extends Producto implements Promocionable{
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
        double ieps = this.precioBase * 0.8; 
        return precioBase + ieps;
    }
    
    
    
    @Override
    public String toString() {
        return super.toString()+"Carne Fria{" + "pesoKg=" + pesoKg + ", tipoCarne=" + tipoCarne + '}';
    }
    
    @Override
    public double aplicarDescuento() {
        if (this.cantidad > 5)
            return this.precioBase *0.05; 
        else 
            return 0.0; 
    }
    
}
