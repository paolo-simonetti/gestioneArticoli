package it.gestionearticoli.web.servlet.articolo;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.model.Articolo;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/ExecuteDeleteArticoloDaListaServlet")
public class ExecuteDeleteArticoloDaListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteDeleteArticoloDaListaServlet() {
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
			Articolo articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(Long.parseLong(request.getParameter("idArticoloDaEliminare")));
			if (articolo==null) {
				request.setAttribute("dangerMessage","Articolo non trovato: non l'avrai impostato a mano da URL, vero???");
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
				return;
			} else {
				MyServiceFactory.getArticoloServiceInstance().rimuovi(articolo);
				TreeSet<Articolo> articoliPresenti=MyServiceFactory.getArticoloServiceInstance().listAll();
				request.setAttribute("listaArticoliAttribute",articoliPresenti);
				request.setAttribute("successMessage","Articolo eliminato con successo");				
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("dangerMessage","Errore nell'esecuzione dell'operazione richiesta");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
			return;
		}
 		request.getRequestDispatcher("articolo/results.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("permessiMancantiMessage","E' stato rilevato un tentativo di cambiare la tipologia di richiesta al server.");
		request.getRequestDispatcher("welcome.jsp").forward(request,response);
		HttpSession session=request.getSession();
		session.invalidate();
	}

}
