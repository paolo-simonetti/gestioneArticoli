package it.gestionearticoli.web.servlet.categoria;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.model.Categoria;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/PrepareDeleteCategoriaServlet")
public class PrepareDeleteCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareDeleteCategoriaServlet() {
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
		String idCategoriaStringInputParam=(String) request.getParameter("idCategoria");
		try {
			Categoria categoriaDaEliminare=MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(Long.parseLong(idCategoriaStringInputParam));
			if (categoriaDaEliminare==null) {
				request.setAttribute("dangerMessage","La categoria selezionata non esiste. Non avrai cercato di inserirla dall'URL, vero???");
				request.getRequestDispatcher("categoria/elencoCategorie.jsp").forward(request,response);
				return;
			} else {
				// Controllo che alla categoria selezionata non afferiscano articoli
				if (MyServiceFactory.getArticoloServiceInstance().elencaArticoliCategoria(Long.parseLong(idCategoriaStringInputParam))!=null) {
					request.setAttribute("dangerMessage","Prima di poter cancellare la categoria, rimuovi tutti gli articoli che afferiscono ad essa!");
					request.setAttribute("listaArticoliAttribute",MyServiceFactory.getArticoloServiceInstance().elencaArticoliCategoria(Long.parseLong(idCategoriaStringInputParam)));
					request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
					return;
				}					
				request.setAttribute("idCategoriaDaEliminare",request.getParameter("idCategoria"));
				request.getRequestDispatcher("categoria/confermaEliminazioneCategoria.jsp").forward(request,response);				
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("dangerMessage","Errore nel reperimento della categoria richiesta, o degli articoli che ad essa afferiscono.");
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
