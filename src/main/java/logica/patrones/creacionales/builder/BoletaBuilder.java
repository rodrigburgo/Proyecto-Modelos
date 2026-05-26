package logica.patrones.creacionales.builder;

import logica.Boleta;
import logica.Evento;

public interface BoletaBuilder {
    BoletaBuilder setPrecio(double precio);
    BoletaBuilder setTipo(String tipo);
    BoletaBuilder setAsiento(String asiento);
    BoletaBuilder setEvento(Evento evento);
    Boleta build();
}
