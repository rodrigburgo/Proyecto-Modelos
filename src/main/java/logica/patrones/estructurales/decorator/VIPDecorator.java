package logica.patrones.estructurales.decorator;

import logica.Boleta;

public class VIPDecorator extends BeneficioDecorator {
    public VIPDecorator(Boleta boleta) { super(boleta); }

    @Override
    public String getDescripcion() {
        return boleta.getDescripcion() + " + Acceso VIP (backstage, bebida)";
    }

    @Override
    public double getPrecio() { return boleta.getPrecio() + 50.0; }
}
