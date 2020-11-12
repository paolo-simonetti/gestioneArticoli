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

@WebServlet("/PrepareUpdateCategoriaDaDropdownServlet")
public class PrepareUpdateCategoriaDaDropdownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareUpdateCategoriaDaDropdownServlet() {
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
				((String) session.getAttribute("codiceFiscaleUtente"))==null||session.getAttribute("ruoloUtente").equals("guest")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		String idCategoriaStringaInput=request.getParameter("idCategoriaDaAggiornare");
		Long idCategoriaInput=(idCategoriaStringaInput.isEmpty()? 0L:Long.parseLong(idCategoriaStringaInput));
		if (idCategoriaInput<=0L) {
			request.setAttribute("errorMessage","L'id della categoria è non valido, nullo o negativo!");
			request.getRequestDispatcher("categoria/updateDaDropdownCategoria.jsp").forward(request,response);
		} else {
			try {
				Categoria categoria=MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(idCategoriaInput);
				if (categoria==null) {
					request.setAttribute("errorMessage","Categoria non presente!");
					request.getRequestDispatcher("categoria/updateDaDropdownCategoria.jsp").forward(request,response);					
				} else {
					request.setAttribute("categoriaDaAggiornare",categoria);
					request.getRequestDispatcher("categoria/updateCategoria.jsp").forward(request,response);
				}
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("dangerMessage","Errore nel reperimento della categoria richiesta");
				request.getRequestDispatcher("menu.jsp").forward(request,response);
			}
		}
	}

}
