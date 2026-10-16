import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Empresa empresa = new Empresa("ModaStyle", "900123456-7", "Armenia, Quindío");

        int opcion = -1;
        do {
            System.out.println("\n======== MENU MODASTYLE =======");
            System.out.println("1. Registrar sucursal");
            System.out.println("2. Registrar prenda en el catálogo");
            System.out.println("3. Registrar cliente");
            System.out.println("4. Registrar vendedor");
            System.out.println("5. Registrar existencias en una sucursal");
            System.out.println("6. Consultar prendas disponibles de una sucursal");
            System.out.println("7. Registrar una venta");
            System.out.println("8. Consultar prendas agotadas");
            System.out.println("9. Consultar ventas de un vendedor");
            System.out.println("10. Consultar compras de un cliente");
            System.out.println("11. Calcular ingresos de una sucursal");
            System.out.println("12. Prenda más vendida de la empresa");
            System.out.println("0. Salir del sistema");

            System.out.println("Selecciona la opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarSucursal(empresa, sc);
                    break;
                case 2:
                    registrarPrenda(empresa, sc);
                    break;
                case 3:
                    registrarCliente(empresa, sc);
                    break;
                case 4:
                    registrarVendedor(empresa, sc);
                    break;
                case 5:
                    registrarInventario(empresa, sc);
                    break;
                case 6:
                    consultarDisponibles(empresa, sc);
                    break;
                case 7:
                    registrarVenta(empresa, sc);
                    break;
                case 8:
                    consultarAgotadas(empresa);
                    break;
                case 9:
                    consultarVentasVendedor(empresa, sc);
                    break;
                case 10:
                    consultarComprasCliente(empresa, sc);
                    break;
                case 11:
                    calcularIngresosSucursal(empresa, sc);
                    break;
                case 12:
                    mostrarPrendaMasVendida(empresa);
                    break;
                case 0:
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida. Intentelo de nuevo");
                    break;
            }

        } while (opcion != 0);
    }

    // 0. Sucursal
    static void registrarSucursal(Empresa empresa, Scanner sc) {
        System.out.println("-------Registrar sucursal-------");
        System.out.println("Código de la sucursal: ");
        String codigo = sc.nextLine();
        System.out.println("Nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Dirección: ");
        String direccion = sc.nextLine();
        System.out.println("Ciudad: ");
        String ciudad = sc.nextLine();

        if (empresa.registrarSucursal(new Sucursal(codigo, nombre, direccion, ciudad))) {
            System.out.println("Sucursal registrada correctamente.");
        } else {
            System.out.println("Error: ya existe una sucursal con ese código.");
        }
    }

    // 1. Prenda
    static void registrarPrenda(Empresa empresa, Scanner sc) {
        System.out.println("-------Registrar prenda-------");
        System.out.println("Código de la prenda: ");
        String codigo = sc.nextLine();
        System.out.println("Nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Marca: ");
        String marca = sc.nextLine();
        System.out.println("Precio base unitario: ");
        double precioBase = sc.nextDouble();
        sc.nextLine();

        System.out.println("Categoría (1. Camiseta, 2. Pantalón, 3. Vestido, 4. Chaqueta, 5. Calzado): ");
        int categoriaOpcion = sc.nextInt();
        sc.nextLine();

        CategoriaPrenda categoria = null;
        if (categoriaOpcion == 1) categoria = CategoriaPrenda.CAMISETA;
        else if (categoriaOpcion == 2) categoria = CategoriaPrenda.PANTALON;
        else if (categoriaOpcion == 3) categoria = CategoriaPrenda.VESTIDO;
        else if (categoriaOpcion == 4) categoria = CategoriaPrenda.CHAQUETA;
        else if (categoriaOpcion == 5) categoria = CategoriaPrenda.CALZADO;

        if (categoria == null) {
            System.out.println("Error: categoría no válida.");
            return;
        }

        if (empresa.registrarPrenda(new Prenda(codigo, nombre, marca, precioBase, categoria))) {
            System.out.println("Prenda registrada correctamente.");
        } else {
            System.out.println("Error: ya existe una prenda con ese código.");
        }
    }

    // 2. Cliente
    static void registrarCliente(Empresa empresa, Scanner sc) {
        System.out.println("-------Registrar cliente-------");
        System.out.println("Documento de identidad: ");
        String documento = sc.nextLine();
        System.out.println("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.println("Correo electrónico: ");
        String correo = sc.nextLine();
        System.out.println("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.println("Código de afiliación: ");
        String codigoAfiliacion = sc.nextLine();

        System.out.println("Categoría (1. Bronce 5%, 2. Plata 10%, 3. Oro 15%): ");
        int categoriaOpcion = sc.nextInt();
        sc.nextLine();

        CategoriaCliente categoria = null;
        if (categoriaOpcion == 1) categoria = CategoriaCliente.BRONCE;
        else if (categoriaOpcion == 2) categoria = CategoriaCliente.PLATA;
        else if (categoriaOpcion == 3) categoria = CategoriaCliente.ORO;

        if (categoria == null) {
            System.out.println("Error: categoría no válida.");
            return;
        }

        Cliente cliente = new Cliente(documento, nombre, correo, telefono, codigoAfiliacion, categoria);
        if (empresa.registrarCliente(cliente)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("Error: ya existe una persona registrada con ese documento.");
        }
    }

    // 2. Vendedor
    static void registrarVendedor(Empresa empresa, Scanner sc) {
        System.out.println("-------Registrar vendedor-------");
        System.out.println("Código de la sucursal donde trabaja: ");
        Sucursal sucursal = empresa.buscarSucursal(sc.nextLine());
        if (sucursal == null) {
            System.out.println("Error: sucursal no encontrada.");
            return;
        }

        System.out.println("Documento de identidad: ");
        String documento = sc.nextLine();
        System.out.println("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.println("Correo electrónico: ");
        String correo = sc.nextLine();
        System.out.println("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.println("Código de empleado: ");
        String codigoEmpleado = sc.nextLine();
        System.out.println("Fecha de contratación (AAAA-MM-DD): ");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(sc.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Error: fecha no válida.");
            return;
        }
        System.out.println("Salario básico: ");
        double salario = sc.nextDouble();
        sc.nextLine();

        Vendedor vendedor = new Vendedor(documento, nombre, correo, telefono, codigoEmpleado, fecha, salario, sucursal);
        if (empresa.registrarVendedor(vendedor)) {
            System.out.println("Vendedor registrado correctamente.");
        } else {
            System.out.println("Error: ya existe una persona registrada con ese documento.");
        }
    }

    // 3. Inventario
    static void registrarInventario(Empresa empresa, Scanner sc) {
        System.out.println("-------Registrar existencias-------");
        System.out.println("Código de la sucursal: ");
        Sucursal sucursal = empresa.buscarSucursal(sc.nextLine());
        if (sucursal == null) {
            System.out.println("Error: sucursal no encontrada.");
            return;
        }

        System.out.println("Código de la prenda: ");
        Prenda prenda = empresa.buscarPrenda(sc.nextLine());
        if (prenda == null) {
            System.out.println("Error: prenda no encontrada en el catálogo.");
            return;
        }

        if (prenda.getCategoria() == CategoriaPrenda.CALZADO) {
            System.out.println("Talla (35 a 44): ");
        } else {
            System.out.println("Talla (XS, S, M, L, XL): ");
        }
        String talla = sc.nextLine().trim().toUpperCase();
        if (!prenda.esTallaValida(talla)) {
            System.out.println("Error: la talla no es válida para la categoría " + prenda.getCategoria());
            return;
        }

        System.out.println("Color: ");
        String color = sc.nextLine().trim();
        System.out.println("Cantidad disponible: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        if (cantidad < 0) {
            System.out.println("Error: la cantidad no puede ser negativa.");
        } else if (sucursal.registrarInventario(prenda, talla, color, cantidad)) {
            System.out.println("Existencias registradas correctamente.");
        } else {
            System.out.println("Error: ya existe un registro para esa prenda, talla y color en la sucursal.");
        }
    }

    // 4. Prendas disponibles
    static void consultarDisponibles(Empresa empresa, Scanner sc) {
        System.out.println("-------Prendas disponibles-------");
        System.out.println("Código de la sucursal: ");
        Sucursal sucursal = empresa.buscarSucursal(sc.nextLine());
        if (sucursal == null) {
            System.out.println("Error: sucursal no encontrada.");
            return;
        }

        List<Inventario> disponibles = sucursal.consultarDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay prendas disponibles en la sucursal " + sucursal.getNombre());
        } else {
            for (Inventario inventario : disponibles) {
                System.out.println(inventario);
            }
        }
    }

    // 5, 6, 7. Venta
    static void registrarVenta(Empresa empresa, Scanner sc) {
        System.out.println("-------Registrar venta-------");
        System.out.println("Código de la sucursal: ");
        Sucursal sucursal = empresa.buscarSucursal(sc.nextLine());
        if (sucursal == null) {
            System.out.println("Error: sucursal no encontrada.");
            return;
        }

        System.out.println("Documento del vendedor: ");
        Vendedor vendedor = empresa.buscarVendedor(sc.nextLine());
        if (vendedor == null || vendedor.getSucursal() != sucursal) {
            System.out.println("Error: vendedor no encontrado en esta sucursal.");
            return;
        }

        System.out.println("Documento del cliente: ");
        Cliente cliente = empresa.buscarCliente(sc.nextLine());
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
            return;
        }

        System.out.println("Código de la venta: ");
        String codigoVenta = sc.nextLine();
        if (empresa.buscarVenta(codigoVenta) != null) {
            System.out.println("Error: ya existe una venta con ese código.");
            return;
        }

        Venta venta = new Venta(codigoVenta, LocalDate.now(), cliente, vendedor, sucursal);

        int otra = 1;
        while (otra == 1) {
            System.out.println("Código de la prenda: ");
            String codigoPrenda = sc.nextLine();
            System.out.println("Talla: ");
            String talla = sc.nextLine().trim().toUpperCase();
            System.out.println("Color: ");
            String color = sc.nextLine().trim();

            Inventario inventario = sucursal.buscarInventario(codigoPrenda, talla, color);
            if (inventario == null) {
                System.out.println("Error: esa prenda, talla y color no están en el inventario de la sucursal.");
            } else {
                System.out.println("Cantidad a comprar: ");
                int cantidad = sc.nextInt();
                sc.nextLine();
                if (venta.agregarDetalle(inventario, cantidad)) {
                    System.out.println("Detalle agregado.");
                } else {
                    System.out.println("Error: cantidad no válida.");
                }
            }

            System.out.println("¿Agregar otra prenda? (1. Sí, 2. No)");
            otra = sc.nextInt();
            sc.nextLine();
        }

        for (DetalleVenta detalle : venta.getListaDetalles()) {
            System.out.println(detalle);
        }
        System.out.printf("Total de la venta: $%,.2f\n", venta.calcularTotal());

        if (venta.confirmar()) {
            System.out.println("Venta confirmada y registrada correctamente.");
        } else {
            System.out.println("La venta no pudo confirmarse: no hay detalles o las cantidades superan las existencias.");
        }
    }

    // 8. Agotadas
    static void consultarAgotadas(Empresa empresa) {
        System.out.println("-------Prendas agotadas-------");
        List<Inventario> agotados = empresa.consultarPrendasAgotadas();
        if (agotados.isEmpty()) {
            System.out.println("No hay prendas agotadas.");
        } else {
            for (Inventario inventario : agotados) {
                System.out.println(inventario.getSucursal().getNombre() + " -> " + inventario);
            }
        }
    }

    // 9. Ventas de un vendedor
    static void consultarVentasVendedor(Empresa empresa, Scanner sc) {
        System.out.println("-------Ventas de un vendedor-------");
        System.out.println("Documento del vendedor: ");
        Vendedor vendedor = empresa.buscarVendedor(sc.nextLine());
        if (vendedor == null) {
            System.out.println("Error: vendedor no encontrado.");
            return;
        }

        if (vendedor.consultarVentas().isEmpty()) {
            System.out.println("El vendedor no tiene ventas registradas.");
        } else {
            for (Venta venta : vendedor.consultarVentas()) {
                System.out.println(venta);
            }
        }
    }

    // 10. Compras de un cliente
    static void consultarComprasCliente(Empresa empresa, Scanner sc) {
        System.out.println("-------Compras de un cliente-------");
        System.out.println("Documento del cliente: ");
        Cliente cliente = empresa.buscarCliente(sc.nextLine());
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
            return;
        }

        if (cliente.consultarCompras().isEmpty()) {
            System.out.println("El cliente no tiene compras registradas.");
        } else {
            for (Venta venta : cliente.consultarCompras()) {
                System.out.println(venta);
            }
        }
    }

    // 11. Ingresos de una sucursal
    static void calcularIngresosSucursal(Empresa empresa, Scanner sc) {
        System.out.println("-------Ingresos de una sucursal-------");
        System.out.println("Código de la sucursal: ");
        Sucursal sucursal = empresa.buscarSucursal(sc.nextLine());
        if (sucursal == null) {
            System.out.println("Error: sucursal no encontrada.");
            return;
        }
        System.out.printf("Los ingresos de la sucursal %s son de $%,.2f\n",
                sucursal.getNombre(), sucursal.calcularIngresos());
    }

    // 12. Prenda más vendida
    static void mostrarPrendaMasVendida(Empresa empresa) {
        System.out.println("-------Prenda más vendida-------");
        Prenda prenda = empresa.prendaMasVendida();
        if (prenda == null) {
            System.out.println("Aún no se han registrado ventas.");
        } else {
            System.out.println(prenda);
            System.out.println("Unidades vendidas: " + prenda.calcularUnidadesVendidas());
        }
    }
}
