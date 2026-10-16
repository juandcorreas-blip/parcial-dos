import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private Sucursal sucursal;
    private Prenda prenda;
    private String talla;
    private String color;
    private int cantidad;
    private List<DetalleVenta> historialVentas;

    public Inventario(Sucursal sucursal, Prenda prenda, String talla, String color, int cantidad) {
        this.sucursal = sucursal;
        this.prenda = prenda;
        this.talla = talla;
        this.color = color;
        this.cantidad = cantidad;
        this.historialVentas = new ArrayList<>();
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Prenda getPrenda() {
        return prenda;
    }

    public void setPrenda(Prenda prenda) {
        this.prenda = prenda;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<DetalleVenta> getHistorialVentas() {
        return historialVentas;
    }

    public void setHistorialVentas(List<DetalleVenta> historialVentas) {
        this.historialVentas = historialVentas;
    }

    @Override
    public String toString() {
        return "Inventario: " +
                "Prenda: " + prenda.getCodigo() + " - " + prenda.getNombre() + " | " +
                "Talla: " + talla + " | " +
                "Color: " + color + " | " +
                "Cantidad: " + cantidad;
    }

    public boolean estaAgotado() {
        return cantidad == 0;
    }

    public void descontar(int unidades) {
        if (unidades > 0 && unidades <= cantidad) {
            cantidad -= unidades;
        }
    }

    public void agregarDetalle(DetalleVenta detalle) {
        historialVentas.add(detalle);
    }

    public int calcularUnidadesVendidas() {
        int unidades = 0;
        for (DetalleVenta detalle : historialVentas) {
            unidades += detalle.getCantidad();
        }
        return unidades;
    }
}