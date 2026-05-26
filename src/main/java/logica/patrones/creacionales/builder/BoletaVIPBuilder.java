package logica.patrones.creacionales.builder;

import logica.Boleta;
import logica.Evento;

public class BoletaVIPBuilder implements BoletaBuilder {
    private Boleta boleta = new Boleta();

    @Override public BoletaBuilder setPrecio(double precio) { boleta.setPrecio(precio * 1.5); return this; }
    @Override public BoletaBuilder setTipo(String tipo) { boleta.setTipo("VIP"); return this; }
    @Override public BoletaBuilder setAsiento(String asiento) { boleta.setAsiento("VIP-" + asiento); return this; }
    @Override public BoletaBuilder setEvento(Evento evento) { boleta.setEvento(evento); return this; }
    @Override public Boleta build() {
        boleta.setDescripcionExtra("Incluye: acceso backstage, bebida de bienvenida, asiento preferencial");
        return boleta;
    }
}
