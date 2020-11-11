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

@WebServlet("/PrepareUpdateCategoriaDaListaServlet")
public class PrepareUpdateCategoriaDaListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareUpdateCategoriaDaListaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		if (((String) session.getAttribute("nomeUtente"))==null||((String) session.getAttribute("cognomeUtente"))==null||
				((String) session.getAttribute("codiceFiscaleUtente"))==null||session.getAttribute("ruoloUtente").equals("guest")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		try {
			Categoria categoriaDaAggiornare=MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(Long.parseLong(request.getParameter("idCategoria")));
			if (categoriaDaAggiornare==null) {
				request.setAttribute("dangerMessage", "Categoria non trovata! Non avrai cercato di inserirla dall'URL, vero???");
				request.getRequestDispatcher("categoria/elencoCategorie.jsp").forward(request,response);
				return;
			} else {
				request.setAttribute("categoriaDaAggiornare",categoriaDaAggiornare);
				request.getRequestDispatcher("categoria/updateCategoria.jsp").forward(request,response);				
			}
		} catch(Exception e) {
			System.err.println("Errore nel reperire la categoria!");
			e.printStackTrace();
		} 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
