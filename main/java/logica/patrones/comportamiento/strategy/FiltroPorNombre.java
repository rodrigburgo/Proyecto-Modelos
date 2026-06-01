package logica.patrones.comportamiento.strategy;

import logica.Evento;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorNombre implements EstrategiaFiltro {
    @Override
    public List<Evento> filtrar(List<Evento> eventos, String criterio) {
        return eventos.stream()
                .filter(e -> e.getNombre().toLowerCase().contains(criterio.toLowerCase()))
                .collect(Collectors.toList());
    }
}
