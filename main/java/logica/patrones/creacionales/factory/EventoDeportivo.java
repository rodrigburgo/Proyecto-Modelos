package logica.patrones.creacionales.factory;

import logica.Evento;

public class EventoDeportivo extends Evento {
    private String equipoLocal;
    private String equipoVisitante;

    public EventoDeportivo(int id, String nombre, String fecha, double precioBase,
                           String lugar, int capacidad, String descripcion,
                           String equipoLocal, String equipoVisitante) {
        super(id, nombre, fecha, precioBase, lugar, capacidad, descripcion, "Deportivo");
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
    }

    @Override
    protected String infoEspecifica() {
        return " | " + equipoLocal + " vs " + equipoVisitante;
    }

    @Override
    public double calcularPrecio() { return precioBase * 1.10; }

    public String getEquipoLocal() { return equipoLocal; }
    public String getEquipoVisitante() { return equipoVisitante; }
}
