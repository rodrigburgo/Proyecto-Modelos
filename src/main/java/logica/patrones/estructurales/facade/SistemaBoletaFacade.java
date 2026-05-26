package logica.patrones.estructurales.facade;

import logica.patrones.comportamiento.chain.ValidadorUsuario;
import logica.patrones.comportamiento.chain.ValidadorDisponibilidad;
import logica.patrones.comportamiento.chain.ValidadorTarjeta;
import logica.patrones.comportamiento.chain.ValidadorCompra;
import logica.patrones.comportamiento.strategy.EstrategiaFiltro;
import logica.patrones.comportamiento.command.ComandoComprarBoleta;
import logica.patrones.estructurales.decorator.VIPDecorator;
import logica.patrones.estructurales.decorator.SeguroDecorator;
import logica.patrones.estructurales.adapter.PagoAdapter;
import logica.patrones.creacionales.singleton.HistorialComandos;
import logica.patrones.creacionales.builder.Director;
import logica.patrones.creacionales.builder.BoletaVIPBuilder;
import logica.patrones.creacionales.builder.BoletaNormalBuilder;
import java.util.List;
import logica.Boleta;
import logica.Evento;
import logica.patrones.estructurales.proxy.PagoProxy;
import logica.Usuario;

public class SistemaBoletaFacade {
    private Director director = new Director();
    private PagoProxy pagoProxy = new PagoProxy();
    private PagoAdapter pagoAdapter = new PagoAdapter();
    private HistorialComandos historial = new HistorialComandos();
    private ValidadorCompra cadenaValidacion;

    public SistemaBoletaFacade() {
        ValidadorUsuario vUsuario = new ValidadorUsuario();
        ValidadorDisponibilidad vDisp = new ValidadorDisponibilidad();
        ValidadorTarjeta vTarjeta = new ValidadorTarjeta();
        vUsuario.setSiguiente(vDisp).setSiguiente(vTarjeta);
        cadenaValidacion = vUsuario;
    }

    public String comprarBoleta(Evento evento, Usuario usuario, String asiento,
                                String tarjeta, String tipo, boolean vip, boolean seguro) {
        String validacion = cadenaValidacion.validar(evento, usuario, tarjeta);
        if (!validacion.equals("OK")) return validacion;

        if (!pagoAdapter.procesarPagoConTarjeta(evento.calcularPrecio(), tarjeta)) {
            return "ERROR: Pago rechazado.";
        }

        director.setBuilder(vip ? new BoletaVIPBuilder() : new BoletaNormalBuilder());
        Boleta boleta = director.construirBoleta(evento, asiento);

        double precioFinal = boleta.getPrecio();
        String descripcion = boleta.getDescripcion();

        if (vip) {
            VIPDecorator vipDec = new VIPDecorator(boleta);
            precioFinal = vipDec.getPrecio();
            descripcion = vipDec.getDescripcion();
        }
        if (seguro) {
            SeguroDecorator seguroDec = new SeguroDecorator(boleta);
            precioFinal = seguroDec.getPrecio();
            descripcion = seguroDec.getDescripcion();
        }

        boleta.setDescripcionExtra(descripcion);
        boleta.setPrecio(precioFinal);

        ComandoComprarBoleta comando = new ComandoComprarBoleta(usuario, boleta, evento);
        historial.ejecutar(comando);

        return "OK: Boleta comprada. " + boleta.getDescripcion() + " | Total: $" + String.format("%.2f", precioFinal);
    }

    public List<Evento> filtrarEventos(List<Evento> eventos, EstrategiaFiltro estrategia, String criterio) {
        return estrategia.filtrar(eventos, criterio);
    }

    public HistorialComandos getHistorial() { return historial; }
}
