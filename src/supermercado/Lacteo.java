package supermercado;


import java.time.LocalDate;

public class Lacteo extends Producto implements Promocionable{
    private String tipoLeche;
    private LocalDate fechaCaducidad;

    // Constructor
    public Lacteo(double precioBase, String nombre, String marca, String categoria, int id, int stock,
                  int cantidad, String tipoLeche, LocalDate fechaCaducidad) {
        super(precioBase, nombre, marca, categoria, id, stock, cantidad);
        this.tipoLeche = tipoLeche;
        this.fechaCaducidad = fechaCaducidad;
    }

    // Constructor vacio
    public Lacteo () {

    }

    // Constructor de Lacteo
    public Lacteo (String tipoLeche, LocalDate fechaCaducidad) {
        this.tipoLeche = tipoLeche;
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String mostrarDetalle() {
        return ">>> Mantengase en refrigeracion.";
    }
    

    


    // Cálculo de precio con un 10% de impuesto por cadena de frío
    @Override
    public double calcularPrecioFinal() {
        return this.precioBase + (this.precioBase * 0.10);
    }

    @Override
    public String toString() {
        return super.toString() + " -> Lacteo{" +
                "tipoLeche='" + tipoLeche + '\'' +
                ", Caduca: " + fechaCaducidad + '\'' +
                '}';
    }      
}