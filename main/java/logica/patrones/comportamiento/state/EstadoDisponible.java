package logica.patrones.comportamiento.state;

public class EstadoDisponible implements EstadoEvento {
    @Override public String getNombre() { return "Disponible"; }
    @Override public boolean permiteCompra() { return true; }
}
