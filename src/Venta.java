import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private String codigo;
    private LocalDate fecha;
    private boolean confirmada;
    private Cliente cliente;
    private Vendedor vendedor;
    private Sucursal sucursal;
    private List<DetalleVenta> listaDetalles;

    public Venta(String codigo, LocalDate fecha, Cliente cliente, Vendedor vendedor, Sucursal sucursal) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.sucursal = sucursal;
        this.confirmada = false;
        this.listaDetalles = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public List<DetalleVenta> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<DetalleVenta> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    @Override
    public String toString() {
        return "Venta: " +
                "Codigo: " + codigo + " | " +
                "Fecha: " + fecha + " | " +
                "Sucursal: " + sucursal.getNombre() + " | " +
                "Cliente: " + cliente.getNombreCompleto() + " | " +
                "Vendedor: " + vendedor.getNombreCompleto() + " | " +
                "Total: " + calcularTotal();
    }

    public boolean agregarDetalle(Inventario inventario, int cantidad) {
        boolean agregado = false;
        if (!confirmada && inventario != null && cantidad > 0) {
            listaDetalles.add(new DetalleVenta(this, inventario, cantidad));
            agregado = true;
        }
        return agregado;
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleVenta detalle : listaDetalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }

    // Verifica que todos los productos sean de la sucursal y que alcancen las existencias,
    // sumando las cantidades cuando se pide varias veces la misma combinación
    public boolean verificarExistencias() {
        boolean suficiente = true;
        for (DetalleVenta detalle : listaDetalles) {
            if (!sucursal.getListaInventarios().contains(detalle.getInventario())) {
                suficiente = false;
            }
            int totalSolicitado = 0;
            for (DetalleVenta otro : listaDetalles) {
                if (otro.getInventario() == detalle.getInventario()) {
                    totalSolicitado += otro.getCantidad();
                }
            }
            if (totalSolicitado > detalle.getInventario().getCantidad()) {
                suficiente = false;
            }
        }
        return suficiente;
    }

    public boolean confirmar() {
        boolean confirmadaConExito = false;
        if (!confirmada && !listaDetalles.isEmpty() && verificarExistencias()) {
            for (DetalleVenta detalle : listaDetalles) {
                detalle.getInventario().descontar(detalle.getCantidad());
                detalle.getInventario().agregarDetalle(detalle);
            }
            confirmada = true;
            sucursal.registrarVenta(this);
            cliente.agregarCompra(this);
            vendedor.agregarVenta(this);
            confirmadaConExito = true;
        }
        return confirmadaConExito;
    }
}
