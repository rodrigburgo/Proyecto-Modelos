package servlet;

import logica.Boleta;
import logica.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/mis-boletas")
public class MisBoletasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Boleta> boletas = usuario.getBoletas();
        List<String> notificaciones = usuario.getNotificaciones();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(generarHtml(usuario, boletas, notificaciones, req));
    }

    private String generarHtml(Usuario usuario, List<Boleta> boletas,
                                List<String> notificaciones, HttpServletRequest req) {
        String ctx = req.getContextPath();
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>")
          .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
          .append("<title>Mis Boletas - EventosMaster</title>")
          .append("<link rel='stylesheet' href='").append(ctx).append("/css/style.css'>")
          .append("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'>")
          .append("</head><body>");

        // Navbar
        sb.append("<nav class='navbar'>")
          .append("<div class='nav-brand'><i class='fas fa-ticket-alt'></i> EventosMaster</div>")
          .append("<div class='nav-links'>")
          .append("<a href='").append(ctx).append("/eventos'><i class='fas fa-arrow-left'></i> Eventos</a>")
          .append("<span class='nav-user'><i class='fas fa-user-circle'></i> ").append(usuario.getNombre()).append("</span>")
          .append("<a href='").append(ctx).append("/logout' class='btn-logout'><i class='fas fa-sign-out-alt'></i> Salir</a>")
          .append("</div></nav>");

        sb.append("<div class='container'>");
        sb.append("<div class='page-title'><h2><i class='fas fa-ticket'></i> Mis Boletas</h2></div>");

        // Estadísticas
        double total = boletas.stream().mapToDouble(Boleta::getPrecio).sum();
        sb.append("<div class='stats-bar'>")
          .append("<div class='stat-card'><i class='fas fa-ticket-alt'></i><div><span class='stat-num'>").append(boletas.size()).append("</span><span>Boletas</span></div></div>")
          .append("<div class='stat-card'><i class='fas fa-dollar-sign'></i><div><span class='stat-num'>$").append(String.format("%,.0f", total)).append("</span><span>Total gastado</span></div></div>")
          .append("<div class='stat-card'><i class='fas fa-bell'></i><div><span class='stat-num'>").append(notificaciones.size()).append("</span><span>Notificaciones</span></div></div>")
          .append("</div>");

        // Boletas
        if (boletas.isEmpty()) {
            sb.append("<div class='sin-resultados'>")
              .append("<i class='fas fa-ticket' style='font-size:3rem;color:var(--gray)'></i>")
              .append("<p>Aún no has comprado boletas.</p>")
              .append("<a href='").append(ctx).append("/eventos' class='btn-comprar'>Ver eventos</a>")
              .append("</div>");
        } else {
            sb.append("<div class='boletas-grid'>");
            for (Boleta b : boletas) {
                String cat = b.getEvento() != null ? b.getEvento().getCategoria() : "Evento";
                String emoji = cat.equalsIgnoreCase("concierto") ? "🎵" :
                               cat.equalsIgnoreCase("deportivo") ? "⚽" : "🎭";
                sb.append("<div class='boleta-card'>")
                  .append("<div class='boleta-header'>")
                  .append("<span class='boleta-emoji'>").append(emoji).append("</span>")
                  .append("<div class='boleta-id'>Boleta #").append(b.getId()).append("</div>")
                  .append("</div>")
                  .append("<div class='boleta-body'>")
                  .append("<h4>").append(b.getEvento() != null ? b.getEvento().getNombre() : "Evento").append("</h4>")
                  .append("<div class='boleta-detalle'><i class='fas fa-chair'></i> Asiento: <strong>").append(b.getAsiento()).append("</strong></div>")
                  .append("<div class='boleta-detalle'><i class='fas fa-tag'></i> Tipo: <strong>").append(b.getTipo()).append("</strong></div>")
                  .append("<div class='boleta-detalle'><i class='fas fa-calendar'></i> ")
                  .append(b.getFechaCompra() != null ? b.getFechaCompra().toString() : "").append("</div>")
                  .append("</div>")
                  .append("<div class='boleta-footer'>")
                  .append("<span class='boleta-precio'>$").append(String.format("%,.0f", b.getPrecio())).append(" COP</span>")
                  .append("</div></div>");
            }
            sb.append("</div>");
        }

        // Notificaciones
        if (!notificaciones.isEmpty()) {
            sb.append("<div class='notif-section'><h3><i class='fas fa-bell'></i> Notificaciones</h3><ul class='notif-list'>");
            for (String n : notificaciones) {
                sb.append("<li><i class='fas fa-info-circle'></i> ").append(n).append("</li>");
            }
            sb.append("</ul></div>");
        }

        sb.append("</div>");
        sb.append("<script src='").append(ctx).append("/js/app.js'></script></body></html>");
        return sb.toString();
    }
}
