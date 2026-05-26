package logica.patrones.comportamiento.iterator;

import logica.Evento;
import java.util.List;

public class EventoIterator {
    private List<Evento> eventos;
    private int posicion = 0;

    public EventoIterator(List<Evento> eventos) { this.eventos = eventos; }

    public boolean hasNext() { return posicion < eventos.size(); }
    public Evento next() { return eventos.get(posicion++); }
    public void reset() { posicion = 0; }
}
