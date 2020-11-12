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

@WebServlet("/PrepareDeleteCategoriaDaDropdownServlet")
public class PrepareDeleteCategoriaDaDropdownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareDeleteCategoriaDaDropdownServlet() {
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
				((String) session.getAttribute("codiceFiscaleUtente"))==null||!session.getAttribute("ruoloUtente").equals("admin")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		String idCategoriaStringaInput=request.getParameter("idCategoriaDaEliminare");
		Long idCategoriaInput=(idCategoriaStringaInput.isEmpty()? 0L:Long.parseLong(idCategoriaStringaInput));
		if (idCategoriaInput<=0L) {
			request.setAttribute("errorMessage","L'id della categoria è non valido, nullo o negativo!");
			request.getRequestDispatcher("categoria/deleteDaDropdownCategoria.jsp").forward(request,response);
		} else {
			try {
				Categoria categoria=MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(idCategoriaInput);
				if (categoria==null) {
					request.setAttribute("errorMessage","Categoria non presente!");
					request.getRequestDispatcher("categoria/deleteDaDropdownCategoria.jsp").forward(request,response);					
				} else {
					if (MyServiceFactory.getArticoloServiceInstance().elencaArticoliCategoria(categoria.getIdCategoria())!=null) {
						request.setAttribute("dangerMessage","Prima di poter cancellare la categoria, rimuovi tutti gli articoli che afferiscono ad essa!");
						request.setAttribute("listaArticoliAttribute",MyServiceFactory.getArticoloServiceInstance().elencaArticoliCategoria(categoria.getIdCategoria()));
						request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
						return;
					}
					request.setAttribute("idCategoriaDaEliminare",categoria.getIdCategoria());
					request.getRequestDispatcher("categoria/confermaEliminazioneCategoria.jsp").forward(request,response);
				}
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("dangerMessage","Errore nel reperimento della categoria richiesta, o nella sua eliminazione.");
				request.getRequestDispatcher("menu.jsp").forward(request,response);
			}
		}
	}

}
