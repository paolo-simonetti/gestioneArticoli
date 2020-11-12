package it.gestionearticoli.web.servlet.categoria;

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

@WebServlet("/GetCategoriaDaListaServlet")
public class GetCategoriaDaListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetCategoriaDaListaServlet() {
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
		String stringaIdCategoriaInput=request.getParameter("idCategoria");
		TreeSet<Articolo> articoliDiCategoriaInput=null;
		Long idCategoria=(stringaIdCategoriaInput.isEmpty()||stringaIdCategoriaInput==null? 0L:Long.parseLong(stringaIdCategoriaInput));
		try {
			articoliDiCategoriaInput=MyServiceFactory.getArticoloServiceInstance().elencaArticoliCategoria(idCategoria); 
			if (articoliDiCategoriaInput==null) {
				request.setAttribute("dangerMessage","Categoria non trovata! Non avrai cercato di impostarla dall'URL, vero???");
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
				return;
			}
			request.setAttribute("listaArticoliAttribute",articoliDiCategoriaInput);
			request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
		} catch(Exception e) {
			request.setAttribute("dangerMessage","Errore nell'esecuzione dell'operazione richiesta");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("permessiMancantiMessage","E' stato rilevato un tentativo di cambiare la tipologia di richiesta al server.");
		request.getRequestDispatcher("welcome.jsp").forward(request,response);
		HttpSession session=request.getSession();
		session.invalidate();
	}

}
