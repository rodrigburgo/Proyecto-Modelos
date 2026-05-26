package logica.patrones.comportamiento.chain;

import logica.Evento;
import logica.Usuario;

public class ValidadorTarjeta extends ValidadorCompra {
    @Override
    public String validar(Evento evento, Usuario usuario, String tarjeta) {
        if (tarjeta == null || tarjeta.replaceAll("\\s", "").length() != 16) {
            return "ERROR: Tarjeta inválida. Debe tener 16 dígitos.";
        }
        return siguiente != null ? siguiente.validar(evento, usuario, tarjeta) : "OK";
    }
}
