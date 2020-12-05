package modelo;

public class ProductoBean {
    private String codigo ;
    private String nombre ;
    private CategoriaBean categoria ;
    private MarcaBean marca ;
    private int existencia ;
    private double precio ;
    private String descripcion ;

    public ProductoBean() {
    }

    public ProductoBean(String codigo, String nombre, CategoriaBean categoria, MarcaBean marca, int existencia, double precio, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.existencia = existencia;
        this.precio = precio;
        this.descripcion = descripcion;
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

    public CategoriaBean getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaBean categoria) {
        this.categoria = categoria;
    }

    public MarcaBean getMarca() {
        return marca;
    }

    public void setMarca(MarcaBean marca) {
        this.marca = marca;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ProductoBean{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", marca=" + marca +
                ", existencia=" + existencia +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
