package it.gestionearticoli.web.servlet.articolo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/PrepareInsertArticoloServlet")
public class PrepareInsertArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PrepareInsertArticoloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		if (((String) session.getAttribute("nomeUtente"))==null||((String) session.getAttribute("cognomeUtente"))==null||
				((String) session.getAttribute("codiceFiscaleUtente"))==null||session.getAttribute("ruoloUtente").equals("guest")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		try {
			request.setAttribute("listaCategorieAttribute",MyServiceFactory.getCategoriaServiceInstance().listAll());			
		} catch(Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("articolo/insert.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
