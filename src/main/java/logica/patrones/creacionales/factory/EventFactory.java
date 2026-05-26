package logica.patrones.creacionales.factory;

import logica.Evento;
import java.util.concurrent.atomic.AtomicInteger;

public class EventFactory {
    private static AtomicInteger counter = new AtomicInteger(1);

    public static Evento crearEvento(String tipo, String nombre, String fecha,
                                     double precio, String lugar, int capacidad,
                                     String descripcion, String extra1, String extra2) {
        int id = counter.getAndIncrement();
        switch (tipo.toLowerCase()) {
            case "concierto":
                return new EventoConcierto(id, nombre, fecha, precio, lugar, capacidad, descripcion, extra1);
            case "deportivo":
                return new EventoDeportivo(id, nombre, fecha, precio, lugar, capacidad, descripcion, extra1, extra2 != null ? extra2 : "");
            case "teatro":
                return new EventoTeatro(id, nombre, fecha, precio, lugar, capacidad, descripcion, extra1);
            default:
                throw new IllegalArgumentException("Tipo de evento no reconocido: " + tipo);
        }
    }
}
