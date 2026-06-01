package logica.patrones.comportamiento.chain;

import logica.Evento;
import logica.Usuario;

public abstract class ValidadorCompra {
    protected ValidadorCompra siguiente;

    public ValidadorCompra setSiguiente(ValidadorCompra siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public abstract String validar(Evento evento, Usuario usuario, String tarjeta);
}
