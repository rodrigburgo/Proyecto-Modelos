package logica.patrones.comportamiento.strategy;

import logica.Evento;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorPrecio implements EstrategiaFiltro {
    @Override
    public List<Evento> filtrar(List<Evento> eventos, String criterio) {
        try {
            double maxPrecio = Double.parseDouble(criterio);
            return eventos.stream()
                    .filter(e -> e.calcularPrecio() <= maxPrecio)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ex) {
            return eventos;
        }
    }
}
