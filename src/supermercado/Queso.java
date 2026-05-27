package supermercado;
import java.time.LocalDate;
public class Queso extends Lacteo {
    protected String tipoQueso;
    protected double pesoKg;
    protected boolean esImportado;

    // Constructor vacio
    public Queso () {
    }

    // Constructor Queso
    public Queso (String tipoQueso, double pesoKg, boolean esImportado) {
        this.tipoQueso = tipoQueso;
        this.pesoKg = pesoKg;
        this.esImportado = esImportado;
    }
    
    // Constructor
    public Queso(double precioBase, String nombre, String marca, String categoria, String id, int stock,
                 int cantidad, String tipoLeche, LocalDate fechaCaducidad,
                 String tipoQueso, double pesoKg, boolean esImportado) {
        super(precioBase, nombre, marca, categoria, id, stock, cantidad, tipoLeche, fechaCaducidad);
        this.tipoQueso = tipoQueso;
        this.pesoKg = pesoKg;
        this.esImportado = esImportado;
    }

    Queso(double precio, String nombre, String marca, String lacteos, String id, int stock, int i, LocalDate fechaCadQ, String tipoQ, double pesoQ, boolean esImportado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double calcularPrecioFinal() {
        // Obtenemos el precio que calcula Lacteo (que ya trae un +10% de refrigeración)
        double precioConLacteo = super.calcularPrecioFinal();
        // Si es importado (true), aplicamos un 10% adicional de comisión
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