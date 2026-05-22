package supermercado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ManejadorArchivos {
    
    // Nombre del archivo donde se guardarán los productos
    private static String archivoInventario = "inventario.str";

    // MÉTODO 1: GUARDAR EL INVENTARIO (ESCRITURA)
    public static void guardarInventario(ArrayList<Producto> lista) {
        // Declaramos los objetos de archivos vacíos fuera del try para poder usarlos en el finally
        FileOutputStream canalArchivo = null;
        ObjectOutputStream escribidorObjetos = null;

        try {
            // Abrimos el archivo binario
            canalArchivo = new FileOutputStream(archivoInventario);
            // Creamos el filtro para poder escribir objetos completos (como el ArrayList)
            escribidorObjetos = new ObjectOutputStream(canalArchivo);
            
            // Escribimos toda la lista de productos de un solo golpe en el archivo
            escribidorObjetos.writeObject(lista);
            System.out.println("-> Inventario guardado exitosamente en el archivo binario.");
            
        } catch (IOException ex) {
            // Si ocurre un error de lectura/escritura, lo atrapamos aquí
            System.out.println("Error al intentar guardar el inventario: " + ex.getMessage());
        } finally {
            // El bloque finally SIEMPRE se ejecuta, sirve para cerrar los archivos obligatoriamente
            try {
                if (escribidorObjetos != null) {
                    escribidorObjetos.close(); // Cerramos el escritor
                }
                if (canalArchivo != null) {
                    canalArchivo.close(); // Cerramos el canal
                }
            } catch (IOException ex) {
                System.out.println("Error al cerrar el archivo de inventario: " + ex.getMessage());
            }
        }
    }

    // MÉTODO 2: LECTURA DEL INVENTARIO (CARGAR AL INICIAR)
    public static ArrayList<Producto> cargarInventario() {
        // Validamos si el archivo físico ya existe en la computadora
        File archivoFisico = new File(archivoInventario);
        if (archivoFisico.exists() == false) {
            // Si el archivo no existe (como la primera vez que corres el programa),
            // regresamos una lista vacía para que el programa no falle.
            return new ArrayList<Producto>();
        }

        FileInputStream canalArchivo = null;
        ObjectInputStream lectorObjetos = null;
        ArrayList<Producto> listaCargada = null;

        try {
            canalArchivo = new FileInputStream(archivoInventario);
            lectorObjetos = new ObjectInputStream(canalArchivo);
            
            // Leemos el objeto del archivo y lo transformamos (cast) a un ArrayList de Productos
            listaCargada = (ArrayList<Producto>) lectorObjetos.readObject();
            return listaCargada;
            
        } catch (IOException ex) {
            System.out.println("Error de lectura al cargar inventario: " + ex.getMessage());
            return new ArrayList<Producto>(); // Si falla, regresa lista vacía
        } catch (ClassNotFoundException ex) {
            System.out.println("Error de conversión de datos: " + ex.getMessage());
            return new ArrayList<Producto>();
        } finally {
            // Cerramos los canales manualmente
            try {
                if (lectorObjetos != null) {
                    lectorObjetos.close();
                }
                if (canalArchivo != null) {
                    canalArchivo.close();
                }
            } catch (IOException ex) {
                System.out.println("Error al cerrar los archivos al leer: " + ex.getMessage());
            }
        }
    }
}