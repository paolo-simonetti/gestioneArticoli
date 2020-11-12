package it.gestionearticoli.web.servlet.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/ListUtentiServlet")
public class ListUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListUtentiServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		if (((String) session.getAttribute("nomeUtente"))==null||((String) session.getAttribute("cognomeUtente"))==null||
				((String) session.getAttribute("codiceFiscaleUtente"))==null||!session.getAttribute("ruoloUtente").equals("admin")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		try {
			request.setAttribute("listaUtentiAttribute", MyServiceFactory.getUtenteServiceInstance().listAll());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("dangerMessage","Errore nel reperimento degli utenti presenti");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
			return;
		}
		request.getRequestDispatcher("utente/elencoUtenti.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
