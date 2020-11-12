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

@WebServlet("/GetArticoloDaDropdownServlet")
public class GetArticoloDaDropdownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetArticoloDaDropdownServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("permessiMancantiMessage","E' stato rilevato un tentativo di cambiare la tipologia di richiesta al server.");
		request.getRequestDispatcher("welcome.jsp").forward(request,response);
		HttpSession session=request.getSession();
		session.invalidate();
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
		String idArticoloStringaInput=request.getParameter("idArticoloDaVisualizzare");
		Long idArticoloInput=(idArticoloStringaInput.isEmpty()? 0L:Long.parseLong(idArticoloStringaInput));
		if (idArticoloInput<=0L) {
			request.setAttribute("errorMessage","L'id dell'articolo è non valido, nullo o negativo!");
			request.getRequestDispatcher("articolo/getDaDropdownArticolo.jsp").forward(request,response);
		} else {
			try {
				Articolo articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idArticoloInput);
				if (articolo==null) {
					request.setAttribute("alertMessage","L'id immesso non ha corrispondenza tra gli articoli presenti!");
				} else {
					TreeSet<Articolo> listaMonoElemento=new TreeSet<>(); //mi serve per poter far arrivare tutto su results.jsp, dove c'è un forEach
					listaMonoElemento.add(articolo);
					request.setAttribute("listaArticoliAttribute",listaMonoElemento);
				}
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("dangerMessage","Errore nella ricerca dell'articolo richiesto.");
				request.getRequestDispatcher("menu.jsp").forward(request,response);
			}
			
		}
	}

}
