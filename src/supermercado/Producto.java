
package supermercado;

public abstract class Producto {
    protected double precioBase;
    protected String nombre;
    protected String marca;
    protected String categoria;
    protected int id;
    protected int stock;
    protected int cantidad;

    // Constructor
    public Producto (double precioBase, String nombre, String marca, String categoria, int id, int stock, int cantidad) {
        this.precioBase = precioBase;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.id = id;
        this.stock = stock;
        this.cantidad = cantidad;
    }
    
    // Constructor vacío
    public Producto () {
        
    }

    @Override
    public String toString() {
        return "Producto{" +
                "precioBase=" + precioBase +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", categoria='" + categoria + '\'' +
                ", id=" + id +
                ", stock=" + stock +
                '}';
    }

    // Método abstracto
    public abstract double calcularPrecioFinal();

}

