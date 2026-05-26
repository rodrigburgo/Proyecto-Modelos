package controladorEventos;

import logica.patrones.comportamiento.strategy.EstrategiaFiltro;
import logica.patrones.creacionales.factory.EventFactory;
import logica.patrones.comportamiento.state.EstadoCancelado;
import logica.Usuario;
import logica.Evento;
import logica.patrones.estructurales.facade.SistemaBoletaFacade;
import java.util.ArrayList;
import java.util.List;

public class EventosController {
    private List<Evento> eventos = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuarioActual;
    private SistemaBoletaFacade facade = new SistemaBoletaFacade();

    public EventosController() {
        cargarDatosDemo();
    }

    private void cargarDatosDemo() {
        // Usuarios de prueba
        usuarios.add(new Usuario(1, "Ana García", "ana@email.com", "123456"));
        usuarios.add(new Usuario(2, "Carlos López", "carlos@email.com", "123456"));
        usuarios.add(new Usuario(3, "María Torres", "maria@email.com", "123456"));

        // Eventos de prueba
        Evento e1 = EventFactory.crearEvento("concierto", "Coldplay: Music of the Spheres",
                "2025-08-15", 180000, "Estadio El Campín, Bogotá", 50000,
                "Gira mundial del legendario grupo británico con espectáculo de luces y fuegos artificiales.",
                "Coldplay", null);
        eventos.add(e1);

        Evento e2 = EventFactory.crearEvento("concierto", "Bad Bunny World Tour",
                "2025-09-20", 220000, "Estadio Atanasio Girardot, Medellín", 45000,
                "El conejo malo regresa a Colombia con su tour más ambicioso.",
                "Bad Bunny", null);
        eventos.add(e2);

        Evento e3 = EventFactory.crearEvento("deportivo", "Final Liga BetPlay",
                "2025-07-30", 80000, "Estadio El Campín, Bogotá", 36000,
                "Gran final del fútbol colombiano. No te pierdas el partido del año.",
                "Millonarios FC", "Atlético Nacional");
        eventos.add(e3);

        Evento e4 = EventFactory.crearEvento("deportivo", "NBA Global Games Colombia",
                "2025-10-05", 250000, "Movistar Arena, Bogotá", 14000,
                "Por primera vez la NBA llega a Colombia con dos equipos de élite.",
                "Miami Heat", "Golden State Warriors");
        eventos.add(e4);

        Evento e5 = EventFactory.crearEvento("teatro", "El Fantasma de la Ópera",
                "2025-08-01", 120000, "Teatro Mayor Julio Mario Santo Domingo", 1800,
                "La producción original de Broadway llega a Bogotá por tiempo limitado.",
                "El Fantasma de la Ópera", null);
        eventos.add(e5);

        Evento e6 = EventFactory.crearEvento("teatro", "Hamlet - Shakespeare",
                "2025-09-10", 60000, "Teatro Colón, Bogotá", 900,
                "La más célebre obra de Shakespeare en una producción contemporánea.",
                "Hamlet", null);
        eventos.add(e6);

        // Suscribir usuarios como observadores
        for (Evento e : eventos) {
            for (Usuario u : usuarios) e.agregarObservador(u);
        }
    }

    public boolean login(String email, String password) {
        for (Usuario u : usuarios) {
            if (u.login(email, password)) {
                usuarioActual = u;
                return true;
            }
        }
        return false;
    }

    public void logout() { usuarioActual = null; }

    public List<Evento> getEventos() { return eventos; }

    public List<Evento> buscar(EstrategiaFiltro estrategia, String criterio) {
        return facade.filtrarEventos(eventos, estrategia, criterio);
    }

    public String comprarBoleta(Evento evento, String asiento, String tarjeta,
                                String tipo, boolean vip, boolean seguro) {
        return facade.comprarBoleta(evento, usuarioActual, asiento, tarjeta, tipo, vip, seguro);
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
    public boolean isLoggedIn() { return usuarioActual != null; }
    public SistemaBoletaFacade getFacade() { return facade; }

    public void cancelarEvento(Evento evento) {
        evento.cambiarEstado(new EstadoCancelado());
    }

    public Evento getEventoPorId(int id) {
        return eventos.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
}
