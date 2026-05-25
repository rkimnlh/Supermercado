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
    //ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream("./src/supermercado/archivoInventario.str"));

    // MÉTODO 1: GUARDAR EL INVENTARIO (ESCRITURA)
    public static void guardarInventario(ArrayList<Producto> lista) {
        // Declaramos los objetos de archivos vacíos fuera del try para poder usarlos en el finally
        FileOutputStream canalArchivo = null;
        ObjectOutputStream escribidorObjetos = null;

        try {
            // Nombre del archivo donde se guardarán los productos
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream("./src/supermercado/archivoInventario.str"));
            // Abrimos el archivo binario
            canalArchivo = new FileOutputStream("./src/supermercado/archivoInventario.str");
            // Creamos el filtro para poder escribir objetos completos (como el ArrayList)
            escribidorObjetos = new ObjectOutputStream(canalArchivo);
            
            // Escribimos toda la lista de productos de un solo golpe en el archivo
            escribidorObjetos.writeObject(lista);
            System.out.println("-> Inventario guardado exitosamente en el archivo binario.");
            
        } catch (IOException e) {
            // Si ocurre un error de lectura/escritura, lo atrapamos aquí
            System.out.println("Error al intentar guardar el inventario: " + e.getMessage());
        } finally {
            // El bloque finally SIEMPRE se ejecuta, sirve para cerrar los archivos obligatoriamente
            try {
                if (escribidorObjetos != null) {
                    escribidorObjetos.close(); // Cerramos el escritor
                }
                if (canalArchivo != null) {
                    canalArchivo.close(); // Cerramos el canal
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo de inventario: " + e.getMessage());
            }
        }
    }

    // MÉTODO 2: LECTURA DEL INVENTARIO (CARGAR AL INICIAR)
    public static ArrayList<Producto> cargarInventario() {
        // Validamos si el archivo físico ya existe en la computadora
        File archivoFisico = new File("./src/supermercado/archivoInventario.str");
        if (archivoFisico.exists() == false) {
            // Si el archivo no existe (como la primera vez que corres el programa),
            // regresamos una lista vacía para que el programa no falle.
            return new ArrayList<Producto>();
        }

        FileInputStream canalArchivo = null;
        ObjectInputStream lectorObjetos = null;
        ArrayList<Producto> listaCargada = null;

        try {
            canalArchivo = new FileInputStream("./src/supermercado/archivoInventario.str");
            lectorObjetos = new ObjectInputStream(canalArchivo);
            
            // Leemos el objeto del archivo y lo transformamos (cast) a un ArrayList de Productos
            listaCargada = (ArrayList<Producto>) lectorObjetos.readObject();
            return listaCargada;
            
        } catch (IOException e) {
            System.out.println("Error de lectura al cargar inventario: " + e.getMessage());
            return new ArrayList<Producto>(); // Si falla, regresa lista vacía
        } catch (ClassNotFoundException e) {
            System.out.println("Error de conversión de datos: " + e.getMessage());
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
            } catch (IOException e) {
                System.out.println("Error al cerrar los archivos al leer: " + e.getMessage());
            }
        }
    }
    private static String archivoVentas = "ventas.dat";

    // MÉTODO 3: GUARDAR HISTORIAL DE VENTAS
    public static void guardarVentas(ArrayList<Venta> listaVentas) {
        FileOutputStream canalArchivo = null;
        ObjectOutputStream escribidorObjetos = null;

        try {
            canalArchivo = new FileOutputStream(archivoVentas);
            escribidorObjetos = new ObjectOutputStream(canalArchivo);
            
            // Guardamos la lista de textos de las ventas realizadas
            escribidorObjetos.writeObject(listaVentas);
            
        } catch (IOException e) {
            System.out.println("Error al intentar guardar el historial de ventas: " + e.getMessage());
        } finally {
            try {
                if (escribidorObjetos != null) {
                    escribidorObjetos.close();
                }
                if (canalArchivo != null) {
                    canalArchivo.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar archivo de ventas: " + e.getMessage());
            }
        }
    }

    // MÉTODO 4: CARGAR HISTORIAL DE VENTAS
    public static ArrayList<Venta> cargarVentas() {
        File archivoFisico = new File(archivoVentas);
        if (archivoFisico.exists() == false) {
            return new ArrayList<Venta>(); // Ahora sí coinciden perfectamente
        }

        FileInputStream canalArchivo = null;
        ObjectInputStream lectorObjetos = null;
        ArrayList<Venta> listaVentasCargadas = null;

        try {
            canalArchivo = new FileInputStream(archivoVentas);
            lectorObjetos = new ObjectInputStream(canalArchivo);
            
            listaVentasCargadas = (ArrayList<Venta>) lectorObjetos.readObject();
            return listaVentasCargadas;
            
        } catch (IOException e) {
            System.out.println("Error al cargar las ventas: " + e.getMessage());
            return new ArrayList<Venta>();
        } catch (ClassNotFoundException e) {
            System.out.println("Error de conversión en ventas: " + e.getMessage());
            return new ArrayList<Venta>();
        } finally {
            try {
                if (lectorObjetos != null) {
                    lectorObjetos.close();
                }
                if (canalArchivo != null) {
                    canalArchivo.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar archivo de lectura de ventas: " + e.getMessage());
            }
        }
    }
    public static class Validador {
        // Si la clave está mal, avisa que hay un error (throws)
        public static void validarClave(String clave) throws IdInvalidaException {
            // .trim() quita los espacios en blanco vacíos. .length() cuenta las letras.
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