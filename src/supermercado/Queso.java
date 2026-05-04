
package supermercado;

import java.time.LocalDate;

public class Queso extends Lacteo {
    private String tipoQueso;
    private double pesoKg;
    private boolean esImportado;

    // Constructor
    public Queso(double precioBase, String nombre, String marca, String categoria, int id, int stock,
                 int cantidad, String tipoLeche, LocalDate fechaCaducidad,
                 String tipoQueso, double pesoKg, boolean esImportado) {
        super(precioBase, nombre, marca, categoria, id, stock, cantidad, tipoLeche, fechaCaducidad);
        this.tipoQueso = tipoQueso;
        this.pesoKg = pesoKg;
        this.esImportado = esImportado;
    }

    // Constructor vacio
    public Queso () {

    }

    // Constructor Queso
    public Queso (String tipoQueso, double pesoKg, boolean esImportado) {
        this.tipoQueso = tipoQueso;
        this.pesoKg = pesoKg;
        this.esImportado = esImportado;
    }

    @Override
    public double calcularPrecioFinal() {
        // Obtenemos el precio que calcula Lacteo (que ya trae un +10% de refrigeración)
        double precioConLacteo = super.calcularPrecioFinal();

        // Si es importado (true), aplicamos un 10% adicional de comisión
        if (this.esImportado) {
            return precioConLacteo * 0.10;
        }

        return precioConLacteo;
    }


    @Override
    public String toString() {
        String origenTexto = esImportado ? "Importado" : "Nacional";
        return super.toString() + " -> Queso{" +
                "tipo='" + tipoQueso + '\'' +
                ", peso=" + pesoKg + "kg" +
                ", origen='" + origenTexto + '\'' +
                ", precioFinal=$" + String.format("%.2f", calcularPrecioFinal()) +
                '}';
    }
}