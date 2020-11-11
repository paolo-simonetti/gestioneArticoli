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

@WebServlet("/PrepareGetCategoriaDaDropdownServlet")
public class PrepareGetCategoriaDaDropdownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareGetCategoriaDaDropdownServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String idCategoriaStringaInput=request.getParameter("idCategoriaDaVisualizzare");
		Long idCategoriaInput=(idCategoriaStringaInput.isEmpty()? 0L:Long.parseLong(idCategoriaStringaInput));
		if (idCategoriaInput<=0L) {
			request.setAttribute("errorMessage","L'id della categoria è non valido, nullo o negativo!");
			request.getRequestDispatcher("categoria/getDaDropdownCategoria.jsp").forward(request,response);
		} else {
			try {
				Categoria categoria=MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(idCategoriaInput);
				if (categoria==null) {
					request.setAttribute("errorMessage","Categoria non presente!");
					request.getRequestDispatcher("getDaDropdownCategoria.jsp").forward(request,response);					
				} else { 
					TreeSet<Categoria> listaCategorieMonoElemento=new TreeSet<>();
					listaCategorieMonoElemento.add(categoria);
					request.setAttribute("listaCategorieAttribute",listaCategorieMonoElemento);
					request.getRequestDispatcher("elencoCategorie.jsp").forward(request,response);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
