import java.util.ArrayList;
import java.util.List;

public class Sucursal {

    private String codigo;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Inventario> listaInventarios;
    private List<Vendedor> listaVendedores;
    private List<Venta> listaVentas;

    public Sucursal(String codigo, String nombre, String direccion, String ciudad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.listaInventarios = new ArrayList<>();
        this.listaVendedores = new ArrayList<>();
        this.listaVentas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Inventario> getListaInventarios() {
        return listaInventarios;
    }

    public void setListaInventarios(List<Inventario> listaInventarios) {
        this.listaInventarios = listaInventarios;
    }

    public List<Vendedor> getListaVendedores() {
        return listaVendedores;
    }

    public void setListaVendedores(List<Vendedor> listaVendedores) {
        this.listaVendedores = listaVendedores;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    @Override
    public String toString() {
        return "Sucursal: " +
                "Codigo: " + codigo + " | " +
                "Nombre: " + nombre + " | " +
                "Direccion: " + direccion + " | " +
                "Ciudad: " + ciudad;
    }

    // ---------- Inventario ----------

    public Inventario buscarInventario(String codigoPrenda, String talla, String color) {
        for (Inventario inventario : listaInventarios) {
            if (inventario.getPrenda().getCodigo().equalsIgnoreCase(codigoPrenda)
                    && inventario.getTalla().equalsIgnoreCase(talla)
                    && inventario.getColor().equalsIgnoreCase(color)) {
                return inventario;
            }
        }
        return null;
    }

    public boolean verificarInventario(String codigoPrenda, String talla, String color) {
        return buscarInventario(codigoPrenda, talla, color) != null;
    }

    // Una combinación de prenda + talla + color solo puede tener un registro en la sucursal
    public boolean registrarInventario(Prenda prenda, String talla, String color, int cantidad) {
        boolean registrado = false;
        boolean existe = verificarInventario(prenda.getCodigo(), talla, color);
        if (!existe && prenda.esTallaValida(talla) && cantidad >= 0) {
            Inventario inventario = new Inventario(this, prenda, talla, color, cantidad);
            listaInventarios.add(inventario);
            prenda.getListaInventarios().add(inventario);
            registrado = true;
        }
        return registrado;
    }

    public List<Inventario> consultarDisponibles() {
        List<Inventario> disponibles = new ArrayList<>();
        for (Inventario inventario : listaInventarios) {
            if (inventario.getCantidad() > 0) {
                disponibles.add(inventario);
            }
        }
        return disponibles;
    }

    public List<Inventario> consultarAgotados() {
        List<Inventario> agotados = new ArrayList<>();
        for (Inventario inventario : listaInventarios) {
            if (inventario.estaAgotado()) {
                agotados.add(inventario);
            }
        }
        return agotados;
    }

    // ---------- Vendedores ----------

    public boolean registrarVendedor(Vendedor vendedor) {
        boolean registrado = false;
        if (!listaVendedores.contains(vendedor)) {
            listaVendedores.add(vendedor);
            registrado = true;
        }
        return registrado;
    }

    // ---------- Ventas ----------

    public Venta buscarVenta(String codigoVenta) {
        for (Venta venta : listaVentas) {
            if (venta.getCodigo().equalsIgnoreCase(codigoVenta)) {
                return venta;
            }
        }
        return null;
    }

    public boolean registrarVenta(Venta venta) {
        boolean registrado = false;
        if (buscarVenta(venta.getCodigo()) == null) {
            listaVentas.add(venta);
            registrado = true;
        }
        return registrado;
    }

    public double calcularIngresos() {
        double ingresos = 0;
        for (Venta venta : listaVentas) {
            if (venta.isConfirmada()) {
                ingresos += venta.calcularTotal();
            }
        }
        return ingresos;
    }
}
