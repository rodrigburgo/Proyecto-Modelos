package servlet;

import controladorEventos.EventosController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        EventosController controller =
                (EventosController) getServletContext().getAttribute("controller");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (controller.login(email, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("usuario", controller.getUsuarioActual());
            resp.sendRedirect(req.getContextPath() + "/eventos");
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.html?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }
}
