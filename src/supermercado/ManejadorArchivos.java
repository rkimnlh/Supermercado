package supermercado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ManejadorArchivos {

    // MÉTODO 1: GUARDAR EL INVENTARIO (ESCRITURA)
    public static void guardarInventario(ArrayList<Producto> lista) {
        // Todo se declara dentro de los paréntesis del try (try-with-resources)
        try (FileOutputStream canalArchivo = new FileOutputStream("./src/supermercado/archivoInventario.str");
             ObjectOutputStream escribidorObjetos = new ObjectOutputStream(canalArchivo)) {
             
            // Escribimos toda la lista de productos de un solo golpe en el archivo
            escribidorObjetos.writeObject(lista);
            System.out.println("-- Inventario actualizado --");
            
        } catch (IOException e) {
            // Si ocurre un error de lectura/escritura, lo atrapamos aquí
            System.out.println("Error al intentar guardar el inventario: " + e.getMessage());
        }
        // Ya no hay finally. Java cierra el archivo automáticamente aquí.
    }

    // MÉTODO 2: LECTURA DEL INVENTARIO (CARGAR AL INICIAR)
    public static ArrayList<Producto> cargarInventario() {
        // Validamos si el archivo físico ya existe en la computadora
        File archivoFisico = new File("./src/supermercado/archivoInventario.str");
        if (!archivoFisico.exists()) {
            // Si el archivo no existe regresamos una lista vacía
            return new ArrayList<Producto>();
        }

        // try-with-resources para la lectura
        try (FileInputStream canalArchivo = new FileInputStream("./src/supermercado/archivoInventario.str");
             ObjectInputStream lectorObjetos = new ObjectInputStream(canalArchivo)) {
             
            // Leemos el objeto del archivo y lo transformamos (cast) a un ArrayList
            return (ArrayList<Producto>) lectorObjetos.readObject();
            
        } catch (IOException e) {
            System.out.println("Error de lectura al cargar inventario: " + e.getMessage());
            return new ArrayList<Producto>(); 
        } catch (ClassNotFoundException e) {
            System.out.println("Error de conversión de datos: " + e.getMessage());
            return new ArrayList<Producto>();
        }
    }

    private static final String ARCHIVO_VENTAS = "ventas.dat";

    // MÉTODO 3: GUARDAR HISTORIAL DE VENTAS
    public static void guardarVentas(ArrayList<Venta> listaVentas) {
        // try-with-resources para la escritura
        try (FileOutputStream canalArchivo = new FileOutputStream(ARCHIVO_VENTAS);
             ObjectOutputStream escribidorObjetos = new ObjectOutputStream(canalArchivo)) {
             
            // Guardamos la lista de ventas
            escribidorObjetos.writeObject(listaVentas);
            
        } catch (IOException e) {
            System.out.println("Error al intentar guardar el historial de ventas: " + e.getMessage());
        }
    }

    // MÉTODO 4: CARGAR HISTORIAL DE VENTAS
    public static ArrayList<Venta> cargarVentas() {
        File archivoFisico = new File(ARCHIVO_VENTAS);
        if (!archivoFisico.exists()) {
            return new ArrayList<Venta>(); 
        }

        // try-with-resources para la lectura
        try (FileInputStream canalArchivo = new FileInputStream(ARCHIVO_VENTAS);
             ObjectInputStream lectorObjetos = new ObjectInputStream(canalArchivo)) {
             
            return (ArrayList<Venta>) lectorObjetos.readObject();
            
        } catch (IOException e) {
            System.out.println("Error al cargar las ventas: " + e.getMessage());
            return new ArrayList<Venta>();
        } catch (ClassNotFoundException e) {
            System.out.println("Error de conversión en ventas: " + e.getMessage());
            return new ArrayList<Venta>();
        }
    }

    public static class Validador {
        // Si la clave está mal, avisa que hay un error (throws)
        public static void validarClave(String clave) throws IdInvalidaException {
            if (clave == null || clave.trim().isEmpty() || clave.trim().length() < 3) {
                throw new IdInvalidaException("La clave debe tener al menos 3 caracteres alfanuméricos.");
            }
        }

        // Si el precio es menor o igual a cero, lanza error
        public static void validarPrecio(double precio) throws PrecioInvalidoException {
            if (precio <= 0) {
                throw new PrecioInvalidoException("El precio debe ser un número positivo mayor a 0.");
            }
        }

        // Si el inventario inicial es menor a cero (negativo), lanza error
        public static void validarStockInicial(int stock) throws StockInvalidoException {
            if (stock < 0) {
                throw new StockInvalidoException("El inventario inicial no puede ser negativo.");
            }
        }
    }
}