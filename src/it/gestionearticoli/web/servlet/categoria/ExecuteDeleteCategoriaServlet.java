package it.gestionearticoli.web.servlet.categoria;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.model.Categoria;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/ExecuteDeleteCategoriaServlet")
public class ExecuteDeleteCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteDeleteCategoriaServlet() {
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
		String idCategoriaInputParam=request.getParameter("idCategoriaDaEliminare");
		Long idCategoria=Long.parseLong(idCategoriaInputParam);
		try {
			Categoria categoriaDaEliminare=MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(idCategoria);
			if (categoriaDaEliminare==null) {
				request.setAttribute("dangerMessage","Categoria non trovata! Non avrai cercato di inserirla a mano dall'URL, vero???");
				request.getRequestDispatcher("categoria/elencoCategorie.jsp").forward(request,response);
				return;
			} else {
				if (MyServiceFactory.getArticoloServiceInstance().elencaArticoliCategoria(categoriaDaEliminare.getIdCategoria())!=null) {
					request.setAttribute("dangerMessage","Prima di poter cancellare la categoria, rimuovi tutti gli articoli che afferiscono ad essa!");
					request.setAttribute("listaArticoliAttribute",MyServiceFactory.getArticoloServiceInstance().elencaArticoliCategoria(categoriaDaEliminare.getIdCategoria()));
					request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
					return;
				}
				MyServiceFactory.getCategoriaServiceInstance().rimuovi(categoriaDaEliminare);
				TreeSet<Categoria> elencoCategorie=MyServiceFactory.getCategoriaServiceInstance().listAll();
				request.setAttribute("listaCategorieAttribute",elencoCategorie);
				request.setAttribute("successMessage","La categoria è stata eliminata correttamente!");
				request.getRequestDispatcher("categoria/elencoCategorie.jsp").forward(request,response);				
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("dangerMessage","Errore nell'esecuzione dell'operazione richiesta");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("permessiMancantiMessage","E' stato rilevato un tentativo di cambiare la tipologia di richiesta al server.");
		request.getRequestDispatcher("welcome.jsp").forward(request,response);
		HttpSession session=request.getSession();
		session.invalidate();
	}

}
