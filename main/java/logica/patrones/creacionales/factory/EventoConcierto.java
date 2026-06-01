package logica.patrones.creacionales.factory;

import logica.Evento;

public class EventoConcierto extends Evento {
    private String artista;

    public EventoConcierto(int id, String nombre, String fecha, double precioBase,
                           String lugar, int capacidad, String descripcion, String artista) {
        super(id, nombre, fecha, precioBase, lugar, capacidad, descripcion, "Concierto");
        this.artista = artista;
    }

    @Override
    protected String infoEspecifica() { return " | Artista: " + artista; }

    @Override
    public double calcularPrecio() { return precioBase * 1.15; }

    public String getArtista() { return artista; }
}
