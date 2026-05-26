package logica.patrones.comportamiento.command;

import logica.Boleta;
import logica.Evento;
import logica.Usuario;

public class ComandoComprarBoleta implements Comando {
    private Usuario usuario;
    private Boleta boleta;
    private Evento evento;
    private boolean ejecutado = false;

    public ComandoComprarBoleta(Usuario usuario, Boleta boleta, Evento evento) {
        this.usuario = usuario;
        this.boleta = boleta;
        this.evento = evento;
    }

    @Override
    public void ejecutar() {
        if (!ejecutado) {
            usuario.agregarBoleta(boleta);
            evento.venderEntrada();
            ejecutado = true;
        }
    }

    @Override
    public void deshacer() {
        // En un sistema real, se revertirían los cambios
        ejecutado = false;
    }

    @Override
    public String getDescripcion() {
        return "Compra de boleta para: " + evento.getNombre() + " por " + usuario.getNombre();
    }
}
