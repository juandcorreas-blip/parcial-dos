import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {

    private String codigoAfiliacion;
    private CategoriaCliente categoria;
    private List<Venta> listaCompras;

    public Cliente(String documento, String nombreCompleto, String correo, String telefono,
                   String codigoAfiliacion, CategoriaCliente categoria) {
        super(documento, nombreCompleto, correo, telefono);
        this.codigoAfiliacion = codigoAfiliacion;
        this.categoria = categoria;
        this.listaCompras = new ArrayList<>();
    }

    public String getCodigoAfiliacion() {
        return codigoAfiliacion;
    }

    public void setCodigoAfiliacion(String codigoAfiliacion) {
        this.codigoAfiliacion = codigoAfiliacion;
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        this.categoria = categoria;
    }

    public List<Venta> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<Venta> listaCompras) {
        this.listaCompras = listaCompras;
    }

    @Override
    public String toString() {
        return "Cliente: " +
                super.toString() + " | " +
                "Afiliacion: " + codigoAfiliacion + " | " +
                "Categoria: " + categoria;
    }

    public void agregarCompra(Venta venta) {
        listaCompras.add(venta);
    }

    public List<Venta> consultarCompras() {
        return listaCompras;
    }
}