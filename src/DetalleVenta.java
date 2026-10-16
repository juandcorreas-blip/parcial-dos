public class DetalleVenta {

    private Venta venta;
    private Inventario inventario;
    private int cantidad;
    private double precioUnitarioFinal;
    private double subtotal;

    // Al crear el detalle se calcula el precio final:
    // precio con recargo de la categoría de la prenda - descuento de la categoría del cliente
    public DetalleVenta(Venta venta, Inventario inventario, int cantidad) {
        this.venta = venta;
        this.inventario = inventario;
        this.cantidad = cantidad;
        this.precioUnitarioFinal = calcularPrecioFinal();
        this.subtotal = precioUnitarioFinal * cantidad;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitarioFinal() {
        return precioUnitarioFinal;
    }

    public void setPrecioUnitarioFinal(double precioUnitarioFinal) {
        this.precioUnitarioFinal = precioUnitarioFinal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Detalle: " +
                inventario.getPrenda().getNombre() + " | " +
                "Talla: " + inventario.getTalla() + " | " +
                "Color: " + inventario.getColor() + " | " +
                "Cantidad: " + cantidad + " | " +
                "Precio unitario final: " + precioUnitarioFinal + " | " +
                "Subtotal: " + subtotal;
    }

    public double calcularPrecioFinal() {
        double precioConRecargo = inventario.getPrenda().calcularPrecioConRecargo();
        double descuento = venta.getCliente().getCategoria().getDescuento();
        return precioConRecargo * (1 - descuento);
    }
}