package servlet;

import logica.patrones.comportamiento.strategy.FiltroPorPrecio;
import logica.patrones.comportamiento.strategy.FiltroPorNombre;
import logica.patrones.comportamiento.strategy.FiltroPorCategoria;
import controladorEventos.EventosController;
import logica.Evento;
import logica.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/eventos")
public class EventosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        EventosController controller =
                (EventosController) getServletContext().getAttribute("controller");

        String busqueda = req.getParameter("busqueda");
        String filtroCat = req.getParameter("categoria");
        String filtroPrecio = req.getParameter("precio");

        List<Evento> eventos = controller.getEventos();

        if (busqueda != null && !busqueda.isEmpty()) {
            eventos = controller.buscar(new FiltroPorNombre(), busqueda);
        } else if (filtroCat != null && !filtroCat.isEmpty()) {
            eventos = controller.buscar(new FiltroPorCategoria(), filtroCat);
        } else if (filtroPrecio != null && !filtroPrecio.isEmpty()) {
            eventos = controller.buscar(new FiltroPorPrecio(), filtroPrecio);
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String msg = req.getParameter("msg");

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(generarHtmlEventos(eventos, usuario, busqueda, filtroCat, filtroPrecio, msg, req));
    }

    private String generarHtmlEventos(List<Evento> eventos, Usuario usuario,
                                       String busqueda, String filtroCat, String filtroPrecio,
                                       String msg, HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        String ctx = req.getContextPath();

        sb.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>")
          .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
          .append("<title>EventosMaster - Eventos</title>")
          .append("<link rel='stylesheet' href='").append(ctx).append("/css/style.css'>")
          .append("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'>")
          .append("</head><body>");

        // Navbar
        sb.append("<nav class='navbar'>")
          .append("<div class='nav-brand'><i class='fas fa-ticket-alt'></i> EventosMaster</div>")
          .append("<div class='nav-links'>")
          .append("<a href='").append(ctx).append("/eventos'><i class='fas fa-home'></i> Inicio</a>")
          .append("<a href='").append(ctx).append("/mis-boletas'><i class='fas fa-ticket'></i> Mis Boletas</a>")
          .append("<span class='nav-user'><i class='fas fa-user-circle'></i> ").append(usuario.getNombre()).append("</span>")
          .append("<a href='").append(ctx).append("/logout' class='btn-logout'><i class='fas fa-sign-out-alt'></i> Salir</a>")
          .append("</div></nav>");

        // Hero
        sb.append("<div class='hero'>")
          .append("<h1>Descubre los Mejores Eventos</h1>")
          .append("<p>Conciertos, deportes, teatro y mucho más</p>")
          .append("</div>");

        // Mensaje
        if (msg != null) {
            boolean ok = msg.startsWith("OK");
            sb.append("<div class='alert ").append(ok ? "alert-success" : "alert-error").append("'>")
              .append("<i class='fas ").append(ok ? "fa-check-circle" : "fa-exclamation-circle").append("'></i> ")
              .append(msg).append("</div>");
        }

        // Filtros
        sb.append("<div class='container'>")
          .append("<div class='filtros-bar'>")
          .append("<form action='").append(ctx).append("/eventos' method='get' class='filtro-form'>")
          .append("<div class='filtro-grupo'>")
          .append("<input type='text' name='busqueda' placeholder='Buscar evento...' value='")
          .append(busqueda != null ? busqueda : "").append("' class='input-search'>")
          .append("<button type='submit' class='btn-buscar'><i class='fas fa-search'></i></button>")
          .append("</div>")
          .append("<div class='filtro-grupo'>")
          .append("<select name='categoria' onchange='this.form.submit()' class='select-filtro'>")
          .append("<option value=''>Todas las categorías</option>")
          .append("<option value='Concierto'").append("Concierto".equals(filtroCat) ? " selected" : "").append(">🎵 Conciertos</option>")
          .append("<option value='Deportivo'").append("Deportivo".equals(filtroCat) ? " selected" : "").append(">⚽ Deportivos</option>")
          .append("<option value='Teatro'").append("Teatro".equals(filtroCat) ? " selected" : "").append(">🎭 Teatro</option>")
          .append("</select>")
          .append("</div>")
          .append("<div class='filtro-grupo'>")
          .append("<select name='precio' onchange='this.form.submit()' class='select-filtro'>")
          .append("<option value=''>Cualquier precio</option>")
          .append("<option value='100000'").append("100000".equals(filtroPrecio) ? " selected" : "").append(">Hasta $100.000</option>")
          .append("<option value='200000'").append("200000".equals(filtroPrecio) ? " selected" : "").append(">Hasta $200.000</option>")
          .append("<option value='500000'").append("500000".equals(filtroPrecio) ? " selected" : "").append(">Hasta $500.000</option>")
          .append("</select>")
          .append("</div>")
          .append("<a href='").append(ctx).append("/eventos' class='btn-limpiar'>Limpiar</a>")
          .append("</form></div>");

        // Grid de eventos
        sb.append("<div class='eventos-grid'>");
        if (eventos.isEmpty()) {
            sb.append("<div class='sin-resultados'><i class='fas fa-search'></i><p>No se encontraron eventos.</p></div>");
        }
        for (Evento e : eventos) {
            String badgeClass = e.getCategoria().equalsIgnoreCase("concierto") ? "badge-concierto" :
                               e.getCategoria().equalsIgnoreCase("deportivo") ? "badge-deportivo" : "badge-teatro";
            String emoji = e.getCategoria().equalsIgnoreCase("concierto") ? "🎵" :
                          e.getCategoria().equalsIgnoreCase("deportivo") ? "⚽" : "🎭";
            String estadoClass = e.getEstado().permiteCompra() ? "estado-ok" : "estado-no";

            sb.append("<div class='evento-card'>")
              .append("<div class='evento-card-header ").append(badgeClass).append("'>")
              .append("<span class='categoria-badge'>").append(emoji).append(" ").append(e.getCategoria()).append("</span>")
              .append("<span class='estado-badge ").append(estadoClass).append("'>").append(e.getEstado().getNombre()).append("</span>")
              .append("</div>")
              .append("<div class='evento-card-body'>")
              .append("<h3>").append(e.getNombre()).append("</h3>")
              .append("<p class='evento-desc'>").append(e.getDescripcion()).append("</p>")
              .append("<div class='evento-info'>")
              .append("<span><i class='fas fa-calendar'></i> ").append(e.getFecha()).append("</span>")
              .append("<span><i class='fas fa-map-marker-alt'></i> ").append(e.getLugar()).append("</span>")
              .append("<span><i class='fas fa-users'></i> ").append(e.getCapacidad() - e.getEntradasVendidas()).append(" disponibles</span>")
              .append("</div>")
              .append("<div class='evento-precio'>$").append(String.format("%,.0f", e.calcularPrecio())).append(" COP</div>")
              .append("</div>")
              .append("<div class='evento-card-footer'>");

            if (e.getEstado().permiteCompra()) {
                sb.append("<a href='").append(ctx).append("/comprar?id=").append(e.getId())
                  .append("' class='btn-comprar'><i class='fas fa-shopping-cart'></i> Comprar Boleta</a>");
            } else {
                sb.append("<button class='btn-comprar disabled' disabled>No disponible</button>");
            }

            sb.append("</div></div>");
        }
        sb.append("</div></div>");

        sb.append("<script src='").append(ctx).append("/js/app.js'></script>");
        sb.append("</body></html>");
        return sb.toString();
    }
}
