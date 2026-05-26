package logica.patrones.creacionales.builder;

import logica.Boleta;
import logica.Evento;

public class BoletaNormalBuilder implements BoletaBuilder {
    private Boleta boleta = new Boleta();

    @Override public BoletaBuilder setPrecio(double precio) { boleta.setPrecio(precio); return this; }
    @Override public BoletaBuilder setTipo(String tipo) { boleta.setTipo(tipo); return this; }
    @Override public BoletaBuilder setAsiento(String asiento) { boleta.setAsiento(asiento); return this; }
    @Override public BoletaBuilder setEvento(Evento evento) { boleta.setEvento(evento); return this; }
    @Override public Boleta build() { return boleta; }
}
