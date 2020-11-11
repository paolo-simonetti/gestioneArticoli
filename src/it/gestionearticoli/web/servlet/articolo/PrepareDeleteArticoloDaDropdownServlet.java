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

@WebServlet("/PrepareDeleteArticoloDaDropdownServlet")
public class PrepareDeleteArticoloDaDropdownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareDeleteArticoloDaDropdownServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String idArticoloStringaInput=(String) request.getParameter("idArticoloDaEliminare");
		Long idArticoloInput=(idArticoloStringaInput.isEmpty()? 0L:Long.parseLong(idArticoloStringaInput));
		if (idArticoloInput<=0L) {
			request.setAttribute("errorMessage","L'id dell'articolo è non valido, nullo o negativo!");
			request.getRequestDispatcher("articolo/deleteDaDropdownArticolo.jsp").forward(request,response);
		} else {
			try {
				Articolo articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idArticoloInput);
				if (articolo==null) {
					request.setAttribute("errorMessage","Articolo non presente!");
					request.getRequestDispatcher("articolo/deleteDaDropdownArticolo.jsp").forward(request,response);					
				} else {
					request.setAttribute("idArticoloDaEliminare",articolo.getId());
					request.getRequestDispatcher("articolo/confermaEliminazione.jsp").forward(request,response);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
