package logica;

import java.util.Date;

public class Boleta {
    private static int contador = 1;
    private int id;
    private double precio;
    private String tipo;
    private String asiento;
    private Evento evento;
    private String descripcionExtra;
    private Date fechaCompra;

    public Boleta() {
        this.id = contador++;
        this.fechaCompra = new Date();
    }

    public String getDescripcion() {
        return String.format("Boleta #%d | %s | Tipo: %s | Asiento: %s | $%.2f | %s",
                id, evento != null ? evento.getNombre() : "N/A", tipo, asiento, precio,
                descripcionExtra != null ? descripcionExtra : "");
    }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getAsiento() { return asiento; }
    public void setAsiento(String asiento) { this.asiento = asiento; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public String getDescripcionExtra() { return descripcionExtra; }
    public void setDescripcionExtra(String descripcionExtra) { this.descripcionExtra = descripcionExtra; }
    public int getId() { return id; }
    public Date getFechaCompra() { return fechaCompra; }

    @Override
    public String toString() { return getDescripcion(); }
}
