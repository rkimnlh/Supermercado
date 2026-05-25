package supermercado;
import java.util.ArrayList;
import java.util.Scanner;

public class SuperMercado {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Cargamos las listas desde los archivos binarios al arrancar
        ArrayList<Producto> inventario = ManejadorArchivos.cargarInventario();
        ArrayList<String> ventas = ManejadorArchivos.cargarVentas();
        
        int opcion = 0;

        do {
            System.out.println("\n=== SECCIÓN DE ARCHIVOS E INVENTARIO ===");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Mostrar Inventario");
            System.out.println("3. Registrar Venta");
            System.out.println("4. Consultar Ventas Realizadas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese ID (Numerico): ");
                        int id = Integer.parseInt(sc.nextLine());
                        
                        System.out.print("Ingrese Nombre del producto: ");
                        String nombre = sc.nextLine();
                        if (nombre.length() < 3) {
                            throw new IdInvalidaException("La clave/nombre debe tener minimo 3 caracteres.");
                        }

                        System.out.print("Ingrese Precio Base: ");
                        double precio = Double.parseDouble(sc.nextLine());
                        if (precio <= 0) {
                            throw new PrecioInvalidoException("El precio debe ser mayor a 0.");
                        }

                        System.out.print("Ingrese Stock Inicial: ");
                        int stock = Integer.parseInt(sc.nextLine());
                        if (stock < 0) {
                            throw new StockInvalidoException("El stock no puede ser menor a 0.");
                        }

                        // Instanciamos usando tu clase existente Fritura
                        Fritura nuevoProducto = new Fritura("Papa", "Sal", 100, precio, nombre, "Sabritas", "Frituras", id, stock, 0);
                        
                        inventario.add(nuevoProducto);
                        ManejadorArchivos.guardarInventario(inventario);
                        System.out.println("¡Producto guardado con exito en el archivo!");

                    } catch (IdInvalidaException e) {
                        System.out.println("Validacion: " + e.getMessage());
                    } catch (PrecioInvalidoException e) {
                        System.out.println("Validacion: " + e.getMessage());
                    } catch (StockInvalidoException e) {
                        System.out.println("Validacion: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de dato incorrecto (Ingreso letras en lugar de numeros).");
                    }
                    break;

                case 2:
                    System.out.println("\n---------------------------------------------------------");
                    System.out.printf("%-6s | %-18s | %-6s | %-12s\n", "ID", "NOMBRE", "STOCK", "PRECIO BASE");
                    System.out.println("---------------------------------------------------------");
                    
                    if (inventario.isEmpty()) {
                        System.out.println("         El inventario esta vacio.");
                    } else {
                        for (int i = 0; i < inventario.size(); i++) {
                            Producto p = inventario.get(i);
                            // Formateamos la salida de cada producto en la tabla
                            System.out.println(String.format("%-6d | %-18.18s | %-6d | $%10.2f", 
                                    p.getId(), p.getNombre(), p.getStock(), p.getPrecioBase()));
                        }
                    }
                    System.out.println("---------------------------------------------------------");
                    break;

                case 3:
                    System.out.print("Ingrese el ID del producto que quiere vender: ");
                    try {
                        int idBuscar = Integer.parseInt(sc.nextLine());
                        Producto encontrado = null;
                        
                        for (int i = 0; i < inventario.size(); i++) {
                            if (inventario.get(i).getId() == idBuscar) {
                                encontrado = inventario.get(i);
                                break;
                            }
                        }

                        if (encontrado == null) {
                            System.out.println("El producto no existe.");
                            break;
                        }

                        System.out.print("Ingrese la cantidad a vender: ");
                        int cantidad = Integer.parseInt(sc.nextLine());
                        
                        if (cantidad <= 0) {
                            throw new StockInvalidoException("La cantidad a vender debe ser mayor a 0.");
                        }
                        if (cantidad > encontrado.getStock()) {
                            throw new StockInvalidoException("No hay suficiente stock. Disponible: " + encontrado.getStock());
                        }

                        // Actualizamos stock
                        encontrado.setStock(encontrado.getStock() - cantidad);
                        
                        // Polimorfismo en acción
                        double importe = cantidad * encontrado.calcularPrecioFinal();
                        
                        // Guardamos registro de venta
                        Venta nuevaVenta = new Venta(encontrado.getNombre(), cantidad, importe);
                        ventas.add(nuevaVenta);
                        
                        // Guardado persistente
                        ManejadorArchivos.guardarInventario(inventario);
                        ManejadorArchivos.guardarVentas(ventas);
                        
                        System.out.println(String.format("¡Venta realizada con exito! Total cobrado: $%.2f", importe));

                    } catch (StockInvalidoException e) {
                        System.out.println("Error en venta: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un dato numerico valido.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- LISTA DE VENTAS DESDE ARCHIVO ---");
                    if (ventas.isEmpty()) {
                        System.out.println("No se han hecho ventas aun.");
                    } else {
                        for (int i = 0; i < ventas.size(); i++) {
                            // Imprime usando el toString() formateado de la clase Venta
                            System.out.println(ventas.get(i).toString());
                        }
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del programa persistente...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 5);
    }
}
