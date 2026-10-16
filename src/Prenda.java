import java.util.ArrayList;
import java.util.List;

public class Prenda {

    private String codigo;
    private String nombre;
    private String marca;
    private double precioBase;
    private CategoriaPrenda categoria;
    private List<Inventario> listaInventarios;

    public Prenda(String codigo, String nombre, String marca, double precioBase, CategoriaPrenda categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.precioBase = precioBase;
        this.categoria = categoria;
        this.listaInventarios = new ArrayList<>();
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public CategoriaPrenda getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPrenda categoria) {
        this.categoria = categoria;
    }

    public List<Inventario> getListaInventarios() {
        return listaInventarios;
    }

    public void setListaInventarios(List<Inventario> listaInventarios) {
        this.listaInventarios = listaInventarios;
    }

    @Override
    public String toString() {
        return "Prenda: " +
                "Codigo: " + codigo + " | " +
                "Nombre: " + nombre + " | " +
                "Marca: " + marca + " | " +
                "Precio base: " + precioBase + " | " +
                "Categoria: " + categoria;
    }

    public double calcularPrecioConRecargo() {
        return precioBase * (1 + categoria.getRecargo());
    }

    // Las tallas válidas dependen de la categoría:
    // calzado -> numéricas de 35 a 44 | demás prendas -> XS, S, M, L, XL
    public boolean esTallaValida(String talla) {
        boolean valida = false;
        if (categoria == CategoriaPrenda.CALZADO) {
            try {
                int numero = Integer.parseInt(talla);
                if (numero >= 35 && numero <= 44) {
                    valida = true;
                }
            } catch (NumberFormatException e) {
                valida = false;
            }
        } else {
            if (talla.equals("XS") || talla.equals("S") || talla.equals("M")
                    || talla.equals("L") || talla.equals("XL")) {
                valida = true;
            }
        }
        return valida;
    }

    public int calcularUnidadesVendidas() {
        int unidades = 0;
        for (Inventario inventario : listaInventarios) {
            unidades += inventario.calcularUnidadesVendidas();
        }
        return unidades;
    }
}