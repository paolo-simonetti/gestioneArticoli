package it.gestionearticoli.web.servlet.articolo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.model.Articolo;
import it.gestionearticoli.service.MyServiceFactory;


@WebServlet("/PrepareDeleteArticoloDaListaServlet")
public class PrepareDeleteArticoloDaListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareDeleteArticoloDaListaServlet() {
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
		String stringaIdArticolo=request.getParameter("idArticoloDaEliminare");
		try {
			Articolo articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(Long.parseLong(stringaIdArticolo));	
			if (articolo==null) {
				request.setAttribute("dangerMessage","Articolo non trovato: non l'avrai impostato a mano da URL, vero???");
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
				return;
			} else {
				request.setAttribute("idArticoloDaEliminare",stringaIdArticolo);
				request.getRequestDispatcher("articolo/confermaEliminazione.jsp").forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("dangerMessage","Errore nella ricerca dell'articolo richiesto");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
