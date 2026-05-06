package supermercado;
public abstract class Producto {
    protected double precioBase;
    protected String nombre;
    protected String marca;
    protected String categoria;
    protected int id;
    protected int stock;
    protected int cantidad;
    
    // Constructor vacío
    public Producto () {
        
    }

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
    
    @Override
    public String toString() {
        return "Producto{" +
                "precioBase=" + precioBase +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", categoria='" + categoria + '\'' +
                ", id=" + id +
                ", stock=" + stock +
                ", cantidad=" + cantidad +
                '}';
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    // Método abstracto
    public abstract double calcularPrecioFinal();
}

