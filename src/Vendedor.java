import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Persona {

    private String codigoEmpleado;
    private LocalDate fechaContratacion;
    private double salarioBasico;
    private Sucursal sucursal;
    private List<Venta> listaVentas;

    public Vendedor(String documento, String nombreCompleto, String correo, String telefono,
                    String codigoEmpleado, LocalDate fechaContratacion, double salarioBasico,
                    Sucursal sucursal) {
        super(documento, nombreCompleto, correo, telefono);
        this.codigoEmpleado = codigoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.salarioBasico = salarioBasico;
        this.sucursal = sucursal;
        this.listaVentas = new ArrayList<>();
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSalarioBasico() {
        return salarioBasico;
    }

    public void setSalarioBasico(double salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    @Override
    public String toString() {
        return "Vendedor: " +
                super.toString() + " | " +
                "Codigo empleado: " + codigoEmpleado + " | " +
                "Contratacion: " + fechaContratacion + " | " +
                "Salario: " + salarioBasico + " | " +
                "Sucursal: " + sucursal.getNombre();
    }

    public void agregarVenta(Venta venta) {
        listaVentas.add(venta);
    }

    public List<Venta> consultarVentas() {
        return listaVentas;
    }
}
