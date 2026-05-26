package logica.patrones.comportamiento.state;

public class EstadoAgotado implements EstadoEvento {
    @Override public String getNombre() { return "Agotado"; }
    @Override public boolean permiteCompra() { return false; }
}
