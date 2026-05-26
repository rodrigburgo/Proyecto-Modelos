package logica;

import logica.patrones.comportamiento.state.EstadoAgotado;
import logica.patrones.comportamiento.state.EstadoDisponible;
import logica.patrones.comportamiento.state.EstadoEvento;
import logica.patrones.comportamiento.observer.EventoObserver;
import java.util.ArrayList;
import java.util.List;

public abstract class Evento {
    protected int id;
    protected String nombre;
    protected String fecha;
    protected double precioBase;
    protected String lugar;
    protected int capacidad;
    protected int entradasVendidas;
    protected String descripcion;
    protected String categoria;
    protected EstadoEvento estado;
    private List<EventoObserver> observadores = new ArrayList<>();

    public Evento(int id, String nombre, String fecha, double precioBase,
                  String lugar, int capacidad, String descripcion, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.precioBase = precioBase;
        this.lugar = lugar;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.entradasVendidas = 0;
        this.estado = new EstadoDisponible();
    }

    public final String mostrarInfo() {
        return String.format("[%s] %s | %s | $%.2f | %s | %d/%d entradas | %s",
                categoria, nombre, fecha, calcularPrecio(), lugar,
                entradasVendidas, capacidad, estado.getNombre()) + infoEspecifica();
    }

    protected abstract String infoEspecifica();
    public abstract double calcularPrecio();

    public void agregarObservador(EventoObserver o) { observadores.add(o); }

    public void notificarObservadores(String mensaje) {
        for (EventoObserver o : observadores) o.actualizar(nombre, mensaje);
    }

    public void cambiarEstado(EstadoEvento nuevoEstado) {
        this.estado = nuevoEstado;
        notificarObservadores("Estado cambiado a: " + nuevoEstado.getNombre());
    }

    public boolean hayDisponibilidad() { return entradasVendidas < capacidad; }

    public void venderEntrada() {
        entradasVendidas++;
        if (entradasVendidas >= capacidad) cambiarEstado(new EstadoAgotado());
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getFecha() { return fecha; }
    public double getPrecioBase() { return precioBase; }
    public String getLugar() { return lugar; }
    public int getCapacidad() { return capacidad; }
    public int getEntradasVendidas() { return entradasVendidas; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria() { return categoria; }
    public EstadoEvento getEstado() { return estado; }

    @Override
    public String toString() { return nombre + " (" + fecha + ")"; }
}
