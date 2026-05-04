package supermercado;


public class Lacteo extends Producto {
    private String tipoLeche;
    private String fechaCaducidad;
    private double contenidoLitros;

    // Constructor
    public Lacteo(double precioBase, String nombre, String marca, String categoria, int id, int stock,
                  int cantidad, String tipoLeche, String fechaCaducidad, double contenidoLitros) {
        super(precioBase, nombre, marca, categoria, id, stock, cantidad);
        this.tipoLeche = tipoLeche;
        this.fechaCaducidad = fechaCaducidad;
        this.contenidoLitros = contenidoLitros;
    }

    // Constructor vacio
    public Lacteo () {

    }

    // Constructor de Lacteo
    public Lacteo (String tipoLeche, String fechaCaducidad, double contenidoLitros) {
        this.tipoLeche = tipoLeche;
        this.fechaCaducidad = fechaCaducidad;
        this.contenidoLitros = contenidoLitros;
    }


    // Cálculo de precio con un 10% de impuesto por cadena de frío
    @Override
    public double calcularPrecioFinal() {
        return this.precioBase * 0.10;
    }

    @Override
    public String toString() {
        return super.toString() + " -> Lacteo{" +
                "tipoLeche='" + tipoLeche + '\'' +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                ", contenidoLitros=" + contenidoLitros +
                '}';
    }
}