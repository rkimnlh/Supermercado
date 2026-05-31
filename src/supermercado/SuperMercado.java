package supermercado;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SuperMercado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Cargamos las listas desde los archivos binarios al arrancar
        ArrayList<Producto> inventario = ManejadorArchivos.cargarInventario();
        ArrayList<Venta> ventas = ManejadorArchivos.cargarVentas();
        
        int opcion = 0;
        System.out.printf("=== BIENVENIDOS A SUPERMERCADO 'STARMOON' ===\n");
        do {
            
            System.out.println("\n=== SECCION DE ARCHIVOS E INVENTARIO ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Registrar venta");
            System.out.println("4. Consultar ventas realizadas");
            System.out.println("5. Eliminar producto del inventario"); 
            System.out.println("6. Salir"); 
            System.out.print("Seleccione una opcion: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
              case 1:
                    try {
                        System.out.println("\n=== REGISTRAR NUEVO PRODUCTO ===");
                        System.out.print("Ingrese ID (Clave alfanumerica): ");
                        String id = scanner.nextLine().trim();

                        // 1. Validación de formato básica de tu validador
                        ManejadorArchivos.Validador.validarClave(id);

                        // 2. CANDADO: Validar que el ID no esté repetido en el inventario actual
                        boolean idExiste = false;
                        // Pasamos a minúsculas el ID que el usuario acaba de teclear
                        String idNuevo = id.toLowerCase();
                        for (int i = 0; i < inventario.size(); i++) {
                            // Extraemos el ID del producto en esta posición del inventario y lo pasamos a minúsculas
                            String idGuardado = inventario.get(i).getId().toLowerCase();
                            // Ahora comparamos ambos textos en minúsculas usando el equals normal
                            if (idGuardado.equals(idNuevo)) {
                                idExiste = true;
                                break; // Ya encontramos que existe, no tiene caso seguir buscando
                            }
                        }

                        // Si el ID ya existe, lanzamos la excepción para interrumpir el flujo
                        if (idExiste) {
                            throw new IdInvalidaException("El ID [" + id + "] ya esta registrado con otro producto.");
                        }
        
                        // ------------------------------------------------------------------
                        // Si el programa llega aquí, significa que el ID es único y legal.
                        // Continúa el flujo normal de captura de datos...
                        // ------------------------------------------------------------------

                        System.out.print("Ingrese nombre del producto: ");
                        String nombre = scanner.nextLine().trim();
                        if (nombre.length() < 3) {
                            throw new IdInvalidaException("El nombre debe tener minimo 3 caracteres.");
                        }

                        System.out.print("Ingrese marca: ");
                        String marca = scanner.nextLine().trim();

                        System.out.print("Ingrese precio base: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        ManejadorArchivos.Validador.validarPrecio(precio);

                        System.out.print("Ingrese stock inicial: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        ManejadorArchivos.Validador.validarStockInicial(stock);
                        
                        System.out.println("\nQue tipo de producto es?");
                        System.out.println("1. Fritura");
                        System.out.println("2. Lacteo");
                        System.out.println("3. Verduras");
                        System.out.println("4. Cereal");         
                        System.out.println("5. Carne Fria");     
                        System.out.print("Seleccione una opcion: ");
                        int tipoProd = Integer.parseInt(scanner.nextLine());

                        Producto nuevoProducto = null; 
                       
                        switch (tipoProd) {
                            case 1: //fritura
                                System.out.print("Ingrese tipo de fritura: ");
                                String tipoF = scanner.nextLine();
                                System.out.print("Ingrese sabor: ");
                                String sabor = scanner.nextLine();
                                System.out.print("Ingrese contenido en gramos: ");
                                double gramos = Double.parseDouble(scanner.nextLine());
                                nuevoProducto = new Fritura(tipoF, sabor, gramos, precio, nombre, marca, "Frituras", id, stock, 0);
                                break;

                            

                            case 2: // Sección de Lácteos
                                System.out.print("Ingrese fecha de caducidad (AAAA-MM-DD): ");
                                String fechaInput = scanner.nextLine().trim();
                                LocalDate fechaC = LocalDate.parse(fechaInput);

                                // SUBMENÚ INTERNO
                                System.out.println("\n--- SUBCLASIFICACION DE LACTEO ---");
                                System.out.println("1. Queso");
                                System.out.println("2. Otro lacteo");
                                System.out.print("Seleccione una opcion: ");
                                int subTipoLacteo = Integer.parseInt(scanner.nextLine());

                                switch (subTipoLacteo) {
                                    case 1: // Queso (No pide tipo de leche)
                                        System.out.println("\n--- DETALLES ESPECIFICOS DEL QUESO ---");
                                        System.out.print("Ingrese tipo de Queso: ");
                                        String tipoQ = scanner.nextLine().trim();
                                        System.out.print("Ingrese peso en Kg: ");
                                        double pesoQ = Double.parseDouble(scanner.nextLine());
                                        System.out.print("¿Es importado? (true/false): ");
                                        boolean esImportado = Boolean.parseBoolean(scanner.nextLine());

                                        // LLAMAMOS AL NUEVO CONSTRUCTOR (Tiene 11 parametros, sin tipoL)
                                        nuevoProducto = new Queso(precio, nombre, marca, "Lacteos", id, stock, 0, fechaC, tipoQ, pesoQ, esImportado);
                                        break;

                                    case 2: // Lácteo Base
                                        System.out.print("Ingrese que tipo de lacteo es: ");
                                        String tipoL = scanner.nextLine().trim();

                                        // Instanciamos el Lacteo normal pasando el tipo que capturamos
                                        nuevoProducto = new Lacteo(precio, nombre, marca, "Lacteos", id, stock, 0, tipoL, fechaC);
                                        break;

                                    default:
                                        System.out.println("Opcion de subclasificacion invalida. Registro de lacteo cancelado.");
                                        break;
                                }
                                break;

                            case 3: // VERDURAS
                                System.out.print("Ingrese el peso en Kg de la muestra/empaque: ");
                                double pesoV = Double.parseDouble(scanner.nextLine());

                                nuevoProducto = new Verduras(pesoV, precio, nombre, marca, "Verduras", id, stock, 0);
                                break;

                            case 4: // CEREAL
                                System.out.print("Ingrese contenido neto en gramos: ");
                               
                                double gramosCereal = Double.parseDouble(scanner.nextLine());
                              
                                System.out.print("Ingrese el tipo de cereal: ");
                                String tipoCereal = scanner.nextLine().trim();
                                
                                nuevoProducto = new Cereal(precio, nombre, marca, "Cereales", id, stock, 0, gramosCereal, tipoCereal);
                                break;

                            case 5: // CARNE FRÍA
                                System.out.print("Ingrese el peso en Kg: ");
                                double pesoCarne = Double.parseDouble(scanner.nextLine());
                                
                                System.out.print("Ingrese el tipo de carne: ");
                                String tipoCarne = scanner.nextLine().trim();
                               
                                nuevoProducto = new CarneFria(precio, nombre, marca, "Carnes Frias", id, stock, 0, pesoCarne, tipoCarne);
                                break;

                            default:
                                System.out.println("Opcion de tipo de producto invalida. Registro cancelado.");
                                break;
                        }

                        // Si se logro instanciar algun objeto del submenú, lo guardamos
                        if (nuevoProducto != null) {
                            inventario.add(nuevoProducto); // Agregamos al ArrayList polimorfico
                            ManejadorArchivos.guardarInventario(inventario); // Guardamos permanentemente en el archivo .dat
                            System.out.println( nuevoProducto.getClass().getSimpleName() + " guardado con exito en el inventario");
                        }

                        } catch (IdInvalidaException | PrecioInvalidoException | StockInvalidoException e) {
                            System.out.println("Validacion: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println(" Error: Formato de numero incorrecto.");
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println(" Error: Formato de fecha invalido. Recuerde usar el formato AAAA-MM-DD.");
                        }
                        break;
                case 2:
                    int subOpcion = 0;
                    do {
                       System.out.println("\n=== VISUALIZAR INVENTARIO ===");
                        System.out.println("1. Inventario general");
                        System.out.println("2. Ver detalles de frituras");
                        System.out.println("3. Ver detalles de lacteos y quesos");
                        System.out.println("4. Ver detalles de verduras");
                        System.out.println("5. Ver detalles de cereales");      
                        System.out.println("6. Ver detalles de carnes frias");  
                        System.out.println("7. Regresar al Menu Principal");    
                        System.out.print("Seleccione una opcion: ");
                        try {
                            subOpcion = Integer.parseInt(scanner.nextLine());

                            if (inventario.isEmpty() && subOpcion != 7) {
                                System.out.println("El inventario esta completamente vacio.");
                                continue;
                            }

                            switch (subOpcion) {
                                case 1:
                                    // LA TABLA GENERAL QUE YA TENÍAN
                                    System.out.println("\n---------------------------------------------------------");
                                    System.out.printf("%-6s | %-18s | %-6s | %-12s\n", "ID", "NOMBRE", "STOCK", "PRECIO BASE");
                                    System.out.println("---------------------------------------------------------");
                                    for (int i = 0; i < inventario.size(); i++) {
                                        Producto p = inventario.get(i);
                                        System.out.println(String.format("%-6s | %-18.18s | %-6d | $%10.2f", 
                                                p.getId(), p.getNombre(), p.getStock(), p.getPrecioBase()));
                                    }
                                    System.out.println("---------------------------------------------------------");
                                    break;

                                case 2: // DETALLES DE FRITURAS
                                case 3: // DETALLES DE LÁCTEOS
                                case 4: // DETALLES DE VERDURAS
                                case 5: //cereales 
                                case 6: //carne fria 
                                    System.out.println("\n--- DETALLES DE CATEGORIA ---");
                                    boolean hayProductos = false;
                                
                                    for (Producto p : inventario) {
                                        if (subOpcion == 2 && p instanceof Fritura) {
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 3 && p instanceof Lacteo) {
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 4 && p instanceof Verduras) {
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 5 && p instanceof Cereal) {      // NUEVO
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        } else if (subOpcion == 6 && p instanceof CarneFria) {   // NUEVO
                                            System.out.println(p.toString());
                                            hayProductos = true;
                                        }
                                    }

                                    if (!hayProductos) {
                                        System.out.println("No hay productos registrados en esta categoria especifica.");
                                    }
                                    break;

                            case 7: 
                                System.out.println("Regresando al menu principal...");
                                break;
                                

                            default:
                                System.out.println("Opcion invalida.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Debe ingresar un numero entero.");
                        }
                    } while (subOpcion != 7);
                    break;

                case 3:
                    System.out.println("\n=== INICIAR NUEVA VENTA ===");

                    // 1. Instanciamos un objeto de tu clase Caja para esta venta
                    Caja cajaActual = new Caja(); 
                    String continuarCaja = "s";

                    // CICLO: Captura de productos y llenado del carrito de la Caja
                    do {
                        System.out.print("Ingrese el ID del producto que quiere vender: ");
                        String idBuscar = scanner.nextLine().trim();
                        Producto encontrado = null;

                        // Buscamos el producto en el inventario global
                        String idBuscarMin = idBuscar.toLowerCase(); // Lo estandarizamos antes del ciclo
                        for (int i = 0; i < inventario.size(); i++) {
                            String idGuardado = inventario.get(i).getId().toLowerCase(); // Extraemos en minúsculas
                            if (idGuardado.equals(idBuscarMin)) { // Comparamos con equals normal
                                encontrado = inventario.get(i);
                                break;
                            }
                        }

                        if (encontrado == null) {
                            System.out.println("El producto no existe en el inventario.");
                        } else {
                            // --- CANDADO DE CADUCIDAD ---
                            boolean puedeVenderse = true;
                            
                            // Aplicamos polimorfismo para ver si el objeto tiene fecha de caducidad
                            if (encontrado instanceof Lacteo) {
                                Lacteo productoLacteo = (Lacteo) encontrado;
                                
                                // Comparamos la fecha de caducidad contra el día de hoy
                                if (productoLacteo.getFechaCaducidad().isBefore(LocalDate.now())) {
                                    System.out.println("ALERTA DE SANIDAD: El producto '" + encontrado.getNombre() + 
                                                       "' caduco el " + productoLacteo.getFechaCaducidad() + " y NO puede ser vendido.");
                                    puedeVenderse = false; // Bajamos la bandera para bloquear la venta
                                }
                            }

                            // Si el producto está en buen estado (o es una fritura/verdura)
                            if (puedeVenderse) {
                                System.out.print("Ingrese la cantidad a vender: ");
                                try {
                                    int cantidad = Integer.parseInt(scanner.nextLine());

                                    if (cantidad <= 0) {
                                        System.out.println("La cantidad debe ser mayor a 0.");
                                    } else if (cantidad > encontrado.getStock()) {
                                        
                                        // AQUÍ LANZAMOS LA EXCEPCIÓN EN LUGAR DE SOLO IMPRIMIR
                                        throw new InsuficienteStockException("No hay suficiente stock. Disponible: " + encontrado.getStock());
                                        
                                    } else {
                                        // Restamos el stock del inventario global
                                        encontrado.setStock(encontrado.getStock() - cantidad);

                                        // Asignamos la cantidad que compra este cliente
                                        encontrado.setCantidad(encontrado.getCantidad() + cantidad); 

                                        // Agregamos el producto directamente al carrito de la caja
                                        cajaActual.getCarrito().add(encontrado);

                                        System.out.println(encontrado.getNombre() + " agregado al carrito de la caja.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ingrese un dato numerico valido.");
                                    
                                // AQUÍ ATRAPAMOS LA EXCEPCIÓN PARA NO CRASHEAR
                                } catch (InsuficienteStockException e) {
                                    System.out.println("Error de Venta: " + e.getMessage());
                                }
                            }
                        }
                        

                        System.out.print("Desea agregar otro producto al carrito? (s/n): ");
                        continuarCaja = scanner.nextLine().trim().toLowerCase();

                    } while (continuarCaja.equals("s"));

                    // PROCESAMIENTO DEL TICKET Y PERSISTENCIA
                    if (!cajaActual.getCarrito().isEmpty()) {
                        // 3. ¡Polimorfismo y encapsulamiento! 
                        // Usamos el toString() de tu clase Caja para imprimir todo el ticket formateado
                        System.out.println(cajaActual.toString());

                        // 4. Registramos de forma persistente las ventas en el historial global
                        for (int i = 0; i < cajaActual.getCarrito().size(); i++) {
                            Producto prodVendido = cajaActual.getCarrito().get(i);
                            int cant = prodVendido.getCantidad();
                            double importeProd = cant * prodVendido.calcularPrecioFinal();

                            // Creamos el registro para tu archivo ventas.dat
                            Venta registroVenta = new Venta(prodVendido.getNombre(), cant, importeProd);
                            ventas.add(registroVenta);

                            // Limpiamos la cantidad asignada al producto para que quede listo en el inventario
                            prodVendido.setCantidad(0);
                        }

                        // 5. Guardado permanente de los archivos binarios actualizados
                        ManejadorArchivos.guardarInventario(inventario);
                        ManejadorArchivos.guardarVentas(ventas);
                        System.out.println("-- Venta finalizada y base de datos guardada con exito. --");
                    } else {
                        System.out.println("-- Venta cancelada. El carrito de la caja esta vacio. --");
                    }
                    break;

                case 4:
                    System.out.println("\n--- LISTA DE VENTAS DESDE ARCHIVO ---");
                    if (ventas.isEmpty()) {
                        System.out.println("No se han hecho ventas aun.");
                    } else {
                        for (int i = 0; i < ventas.size(); i++) {
                            System.out.println(ventas.get(i).toString());
                        }
                    }
                    break;
                case 5:
                    System.out.println("\n=== ELIMINAR PRODUCTO DEL INVENTARIO ===");
                    System.out.print("Ingrese el ID del producto que desea borrar: ");
                    String idBorrar = scanner.nextLine().trim();

                    boolean encontradoParaBorrar = false;

                    // Buscamos de forma clásica en el ArrayList
                    String idBorrarMin = idBorrar.toLowerCase(); // Lo estandarizamos antes del ciclo
                    for (int i = 0; i < inventario.size(); i++) {
                        String idGuardado = inventario.get(i).getId().toLowerCase(); // Extraemos en minúsculas
                        if (idGuardado.equals(idBorrarMin)) { // Comparamos con equals normal

                            // Guardamos el nombre antes de borrarlo para avisarle al usuario
                            String nombreEliminado = inventario.get(i).getNombre();

                            // 1. Lo removemos del ArrayList en memoria RAM
                            inventario.remove(i); 

                            // 2. Sobrescribimos el archivo binario para actualizar el disco duro
                            ManejadorArchivos.guardarInventario(inventario); 

                            System.out.println("¡Exito! El producto '" + nombreEliminado + "' con ID [" + idBorrar + "] fue eliminado.");
                            encontradoParaBorrar = true;
                             // Salimos del ciclo porque los IDs son únicos
                        }
                    }
                    if (!encontradoParaBorrar) 
                        System.out.println("No se encontro ningun producto con el ID [" + idBorrar + "].");
                    break;
                case 6:
                    System.out.println("Saliendo del programa persistente...");
                    break;

                default:
                    System.out.println("Opcion invalida.");
                }
            
            } while (opcion != 6);
        }
}