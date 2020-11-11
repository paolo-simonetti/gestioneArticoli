package it.gestionearticoli.web.servlet.articolo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/ExecuteGetArticoloServlet")
public class ExecuteGetArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteGetArticoloServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		if (((String) session.getAttribute("nomeUtente"))==null||((String) session.getAttribute("cognomeUtente"))==null||
				((String) session.getAttribute("codiceFiscaleUtente"))==null) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		String idDaInputParam = request.getParameter("idArticolo");
		Long idArticolo = !idDaInputParam.isEmpty() ? Long.parseLong(idDaInputParam) : 0;
		if (idDaInputParam.isEmpty() || idArticolo < 1) {
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("articolo/get.jsp").forward(request, response);
			return;
		}

		try {
			request.setAttribute("articoloAttribute", 
					MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idArticolo));
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("articolo/getResult.jsp").forward(request, response);
	}

}
