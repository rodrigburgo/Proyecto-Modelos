package logica.patrones.creacionales.builder;

import logica.Boleta;
import logica.Evento;

public class Director {
    private BoletaBuilder builder;

    public void setBuilder(BoletaBuilder builder) { this.builder = builder; }

    public Boleta construirBoleta(Evento evento, String asiento) {
        return builder
                .setEvento(evento)
                .setPrecio(evento.calcularPrecio())
                .setTipo(builder instanceof BoletaVIPBuilder ? "VIP" : "Normal")
                .setAsiento(asiento)
                .build();
    }
}
