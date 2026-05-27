package supermercado;
import java.time.LocalDate;

public class Queso extends Lacteo {
    protected String tipoQueso;
    protected double pesoKg;
    protected boolean esImportado;

    // Constructor vacio
    public Queso () {
    }

    // CONSTRUCTOR ESPECIAL: Sin tipo de leche en los parametros
    public Queso(double precioBase, String nombre, String marca, String categoria, String id, int stock,
                 int cantidad, LocalDate fechaCaducidad, String tipoQueso, double pesoKg, boolean esImportado) {
        
        // Le pasamos "No especificado" al constructor de Lacteo para que no truene ni quede en null
        super(precioBase, nombre, marca, categoria, id, stock, cantidad, "No especificado", fechaCaducidad);
        
        this.tipoQueso = tipoQueso;
        this.pesoKg = pesoKg;
        this.esImportado = esImportado;
    }

    @Override
    public double calcularPrecioFinal() {
        double precioConLacteo = super.calcularPrecioFinal();
        if (this.esImportado) {
            return precioConLacteo * 1.10;
        }
        return precioConLacteo;
    }

    @Override
    public String toString() {
        String origenTexto = this.esImportado ? "Importado" : "Nacional";
        return String.format("%s | Queso: %s | Peso: %.2f kg | Origen: %s | Final: $%.2f", 
                             super.toString(), this.tipoQueso, this.pesoKg, origenTexto, this.calcularPrecioFinal());
    }
}