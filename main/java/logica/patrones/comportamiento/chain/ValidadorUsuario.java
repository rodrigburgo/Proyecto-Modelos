package logica.patrones.comportamiento.chain;

import logica.Evento;
import logica.Usuario;

public class ValidadorUsuario extends ValidadorCompra {
    @Override
    public String validar(Evento evento, Usuario usuario, String tarjeta) {
        if (usuario == null) return "ERROR: Usuario no autenticado.";
        return siguiente != null ? siguiente.validar(evento, usuario, tarjeta) : "OK";
    }
}
