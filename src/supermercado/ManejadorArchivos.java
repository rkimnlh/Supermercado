package supermercado;
//@author starmoon 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ManejadorArchivos {

    // metodo para guardar el inventario en el archivo fisico
    public static void guardarInventario(ArrayList<Producto> lista) {
        // usamos try-with-resources para que el archivo no se quede abierto y no tengamos fugas de memoria o que marque error de acceso despues
        try (FileOutputStream canalArchivo = new FileOutputStream("./src/supermercado/archivoInventario.str");
             ObjectOutputStream escribidorObjetos = new ObjectOutputStream(canalArchivo)) {
             
            // mandamos toda la lista completa al archivo de un solo golpe
            escribidorObjetos.writeObject(lista);
            System.out.println("-- Inventario actualizado --");
            
        } catch (IOException e) {
            //si ocurre algun error, atrapamos la excepcion 
            System.out.println("Error al intentar guardar el inventario: " + e.getMessage());
        }
    }

    // cargar el inventario al arrancar el sistema
    public static ArrayList<Producto> cargarInventario() {
        //revisamos si el archivo existe 
        File archivoFisico = new File("./src/supermercado/archivoInventario.str");
        if (!archivoFisico.exists()) {
            // si es la primera vez que lo corremos, regresamos lista vacia
            return new ArrayList<Producto>();
        }

        // abrimos los canales de lectura en el try 
        try (FileInputStream canalArchivo = new FileInputStream("./src/supermercado/archivoInventario.str");
             ObjectInputStream lectorObjetos = new ObjectInputStream(canalArchivo)) {
             
            //leemos la lista de productos 
            return (ArrayList<Producto>) lectorObjetos.readObject();
            
        } catch (IOException e) {
            System.out.println("Error de lectura al cargar inventario: " + e.getMessage());
            return new ArrayList<Producto>(); 
        } catch (ClassNotFoundException e) {
            System.out.println("Error de conversion de datos: " + e.getMessage());
            return new ArrayList<Producto>();
        }
    }

    private static final String ARCHIVO_VENTAS = "ventas.dat";

    //guardamos las ventas 
    public static void guardarVentas(ArrayList<Venta> listaVentas) {
        // try-with-resources para escribir 
        try (FileOutputStream canalArchivo = new FileOutputStream(ARCHIVO_VENTAS);
             ObjectOutputStream escribidorObjetos = new ObjectOutputStream(canalArchivo)) {
    
            // sobrescribimos con la lista nueva actualizada
            escribidorObjetos.writeObject(listaVentas);
            
        } catch (IOException e) {
            System.out.println("Error al intentar guardar el historial de ventas: " + e.getMessage());
        }
    }

    //recuperar las ventas
    public static ArrayList<Venta> cargarVentas() {
        File archivoFisico = new File(ARCHIVO_VENTAS);
        if (!archivoFisico.exists()) {
            return new ArrayList<Venta>(); // por si todavia no hay ventas registradas
        }

        // leemos el archivo de ventas
        try (FileInputStream canalArchivo = new FileInputStream(ARCHIVO_VENTAS);
             ObjectInputStream lectorObjetos = new ObjectInputStream(canalArchivo)) {
             
            return (ArrayList<Venta>) lectorObjetos.readObject();
            
        } catch (IOException e) {
            System.out.println("Error al cargar las ventas: " + e.getMessage());
            return new ArrayList<Venta>();
        } catch (ClassNotFoundException e) {
            System.out.println("Error de conversion en ventas: " + e.getMessage());
            return new ArrayList<Venta>();
        }
    }

    // clase validador para atrapar los errores del usuario al registrar
    public static class Validador {
        // lanza el error si la clave viene vacia o menor a 3 digitos 
        public static void validarClave(String clave) throws IdInvalidaException {
            if (clave == null || clave.trim().isEmpty() || clave.trim().length() < 3) {//usamos .trim para eliminar cualquier espacio
                throw new IdInvalidaException("La clave debe tener al menos 3 caracteres alfanumericos.");
            }
        }

        // lanza el error si quiere registrar un precio en cero o negativo
        public static void validarPrecio(double precio) throws PrecioInvalidoException {
            if (precio <= 0) {
                throw new PrecioInvalidoException("El precio debe ser un numero positivo mayor a 0.");
            }
        }

        // lanza error si quiere registrar un inventario en numeros negativos
        public static void validarStockInicial(int stock) throws StockInvalidoException {
            if (stock < 0) {
                throw new StockInvalidoException("El inventario inicial no puede ser negativo.");
            }
        }
    }
}