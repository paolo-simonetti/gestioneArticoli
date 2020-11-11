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

@WebServlet("/GetArticoloDaListaServlet")
public class GetArticoloDaListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetArticoloDaListaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		if (((String) session.getAttribute("nomeUtente"))==null||((String) session.getAttribute("cognomeUtente"))==null||
				((String) session.getAttribute("codiceFiscaleUtente"))==null) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		String stringaIdArticolo=request.getParameter("idArticoloDaVisualizzare");
		Long idArticolo = Long.parseLong(stringaIdArticolo);	
		try {
			Articolo articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idArticolo);
			if (articolo==null) {
				request.setAttribute("dangerMessage","Articolo non trovato: non l'avrai impostato a mano da URL, vero???");
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
				return;
			} else {
				TreeSet<Articolo> listaMonoElemento=new TreeSet<>(); //mi serve per poter far arrivare tutto su results.jsp, dove c'è un forEach
				listaMonoElemento.add(articolo);
				request.setAttribute("listaArticoliAttribute",listaMonoElemento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("articolo/results.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
