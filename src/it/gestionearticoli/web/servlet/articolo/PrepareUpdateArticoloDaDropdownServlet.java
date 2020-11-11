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
import it.gestionearticoli.model.Categoria;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/PrepareUpdateArticoloDaDropdownServlet")
public class PrepareUpdateArticoloDaDropdownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareUpdateArticoloDaDropdownServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String idArticoloStringaInput=(String) request.getParameter("idArticoloDaAggiornare");
		Long idArticoloInput=(idArticoloStringaInput.isEmpty()? 0L:Long.parseLong(idArticoloStringaInput));
		if (idArticoloInput<=0L) {
			request.setAttribute("errorMessage","L'id dell'articolo è non valido, nullo o negativo!");
			request.getRequestDispatcher("articolo/updateDaDropdownArticolo.jsp").forward(request,response);
		} else {
			try {
				Articolo articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idArticoloInput);
				if (articolo==null) {
					request.setAttribute("errorMessage","Articolo non presente!");
					request.getRequestDispatcher("articolo/updateDaDropdownArticolo.jsp").forward(request,response);					
				} else {
					request.setAttribute("articoloDaAggiornare",articolo);
					TreeSet<Categoria> listaCategorieAttribute=MyServiceFactory.getCategoriaServiceInstance().listAll();
					request.setAttribute("listaCategorieAttribute",listaCategorieAttribute);
					request.getRequestDispatcher("articolo/update.jsp").forward(request,response);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
