import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nombreComercial;
    private String nit;
    private String direccionPrincipal;
    private List<Sucursal> listaSucursales;
    private List<Prenda> listaPrendas;
    private List<Cliente> listaClientes;
    private List<Vendedor> listaVendedores;

    public Empresa(String nombreComercial, String nit, String direccionPrincipal) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccionPrincipal = direccionPrincipal;
        this.listaSucursales = new ArrayList<>();
        this.listaPrendas = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaVendedores = new ArrayList<>();
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Prenda> getListaPrendas() {
        return listaPrendas;
    }

    public void setListaPrendas(List<Prenda> listaPrendas) {
        this.listaPrendas = listaPrendas;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Vendedor> getListaVendedores() {
        return listaVendedores;
    }

    public void setListaVendedores(List<Vendedor> listaVendedores) {
        this.listaVendedores = listaVendedores;
    }

    @Override
    public String toString() {
        return "Empresa: " +
                "Nombre: " + nombreComercial + " | " +
                "NIT: " + nit + " | " +
                "Direccion: " + direccionPrincipal;
    }

    // ---------- Sucursales ----------

    public Sucursal buscarSucursal(String codigo) {
        for (Sucursal sucursal : listaSucursales) {
            if (sucursal.getCodigo().equalsIgnoreCase(codigo)) {
                return sucursal;
            }
        }
        return null;
    }

    public boolean registrarSucursal(Sucursal sucursal) {
        boolean registrado = false;
        if (buscarSucursal(sucursal.getCodigo()) == null) {
            listaSucursales.add(sucursal);
            registrado = true;
        }
        return registrado;
    }

    // ---------- 1. Prendas ----------

    public Prenda buscarPrenda(String codigo) {
        for (Prenda prenda : listaPrendas) {
            if (prenda.getCodigo().equalsIgnoreCase(codigo)) {
                return prenda;
            }
        }
        return null;
    }

    public boolean registrarPrenda(Prenda prenda) {
        boolean registrado = false;
        if (buscarPrenda(prenda.getCodigo()) == null) {
            listaPrendas.add(prenda);
            registrado = true;
        }
        return registrado;
    }

    // ---------- 2. Clientes y vendedores ----------

    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDocumento().equalsIgnoreCase(documento)) {
                return cliente;
            }
        }
        return null;
    }

    public Vendedor buscarVendedor(String documento) {
        for (Vendedor vendedor : listaVendedores) {
            if (vendedor.getDocumento().equalsIgnoreCase(documento)) {
                return vendedor;
            }
        }
        return null;
    }

    // El documento no puede repetirse entre clientes y vendedores
    public boolean verificarDocumento(String documento) {
        return buscarCliente(documento) != null || buscarVendedor(documento) != null;
    }

    public boolean registrarCliente(Cliente cliente) {
        boolean registrado = false;
        if (!verificarDocumento(cliente.getDocumento())) {
            listaClientes.add(cliente);
            registrado = true;
        }
        return registrado;
    }

    public boolean registrarVendedor(Vendedor vendedor) {
        boolean registrado = false;
        if (!verificarDocumento(vendedor.getDocumento())) {
            listaVendedores.add(vendedor);
            vendedor.getSucursal().registrarVendedor(vendedor);
            registrado = true;
        }
        return registrado;
    }

    // ---------- Consultas generales ----------

    // 8. Prendas (registros de inventario) agotadas en todas las sucursales
    public List<Inventario> consultarPrendasAgotadas() {
        List<Inventario> agotados = new ArrayList<>();
        for (Sucursal sucursal : listaSucursales) {
            agotados.addAll(sucursal.consultarAgotados());
        }
        return agotados;
    }

    // Una venta se identifica por un código único en toda la empresa
    public Venta buscarVenta(String codigoVenta) {
        for (Sucursal sucursal : listaSucursales) {
            Venta venta = sucursal.buscarVenta(codigoVenta);
            if (venta != null) {
                return venta;
            }
        }
        return null;
    }

    // 12. Referencia de prenda con más unidades vendidas en toda la empresa
    public Prenda prendaMasVendida() {
        Prenda masVendida = null;
        int maximo = 0;
        for (Prenda prenda : listaPrendas) {
            int unidades = prenda.calcularUnidadesVendidas();
            if (unidades > maximo) {
                maximo = unidades;
                masVendida = prenda;
            }
        }
        return masVendida;
    }
}