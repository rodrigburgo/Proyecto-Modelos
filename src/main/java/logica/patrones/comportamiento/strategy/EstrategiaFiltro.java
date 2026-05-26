package logica.patrones.comportamiento.strategy;

import logica.Evento;
import java.util.List;

public interface EstrategiaFiltro {
    List<Evento> filtrar(List<Evento> eventos, String criterio);
}
