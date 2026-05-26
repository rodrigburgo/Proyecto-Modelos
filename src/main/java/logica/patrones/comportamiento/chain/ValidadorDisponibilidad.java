package logica.patrones.comportamiento.chain;

import logica.Evento;
import logica.Usuario;

public class ValidadorDisponibilidad extends ValidadorCompra {
    @Override
    public String validar(Evento evento, Usuario usuario, String tarjeta) {
        if (!evento.hayDisponibilidad()) return "ERROR: No hay entradas disponibles.";
        if (!evento.getEstado().permiteCompra()) return "ERROR: El evento no permite compras (" + evento.getEstado().getNombre() + ").";
        return siguiente != null ? siguiente.validar(evento, usuario, tarjeta) : "OK";
    }
}
