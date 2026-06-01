package logica.patrones.comportamiento.command;

public interface Comando {
    void ejecutar();
    void deshacer();
    String getDescripcion();
}
