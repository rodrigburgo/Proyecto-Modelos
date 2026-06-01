package logica.patrones.creacionales.factory;

import logica.Evento;

public class EventoTeatro extends Evento {
    private String obra;

    public EventoTeatro(int id, String nombre, String fecha, double precioBase,
                        String lugar, int capacidad, String descripcion, String obra) {
        super(id, nombre, fecha, precioBase, lugar, capacidad, descripcion, "Teatro");
        this.obra = obra;
    }

    @Override
    protected String infoEspecifica() { return " | Obra: " + obra; }

    @Override
    public double calcularPrecio() { return precioBase * 1.05; }

    public String getObra() { return obra; }
}
