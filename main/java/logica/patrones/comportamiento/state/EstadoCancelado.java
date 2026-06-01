package logica.patrones.comportamiento.state;

public class EstadoCancelado implements EstadoEvento {
    @Override public String getNombre() { return "Cancelado"; }
    @Override public boolean permiteCompra() { return false; }
}
