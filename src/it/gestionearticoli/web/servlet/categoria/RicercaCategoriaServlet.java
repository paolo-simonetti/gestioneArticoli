package it.gestionearticoli.web.servlet.categoria;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticoli.model.Categoria;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/RicercaCategoriaServlet")
public class RicercaCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RicercaCategoriaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeCategoriaInputParam=request.getParameter("nomeCategoria");
		if (nomeCategoriaInputParam==null||nomeCategoriaInputParam.isEmpty()) {
			try {
				request.setAttribute("listaCategorieAttribute",MyServiceFactory.getCategoriaServiceInstance().listAll());
				request.getRequestDispatcher("categoria/elencoCategorie.jsp").forward(request,response);
				return;
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			/* L'input di ricerca potrebbe essere "giocattoli divertentissimi", e voglio che tra i risultati compaiano anche categorie 
			  il cui nome è "dei bei giocattoli", "divertentissimi giocattoli", "scherzi divertentissimi". */
			String[] pezziComponentiNomeCategoriaInputParam=nomeCategoriaInputParam.trim().split("\\s+");
			// La stringa precedente potrebbe risultare qualcosa come ('giocattoli',' ','divertentissimi'); allora rimuovo gli elementi vuoti.
			TreeSet<String> pezziComponentiInput=new TreeSet<>();
			for (int i=0; i<pezziComponentiNomeCategoriaInputParam.length; i++) {
				if (!(pezziComponentiNomeCategoriaInputParam[i].isEmpty())) {
					pezziComponentiInput.add(pezziComponentiNomeCategoriaInputParam[i]);
				} 
			}
			// Ora che ho la collection di parole su cui effettuare la ricerca, la passo al metodo che effettua la ricerca.
			try {
				TreeSet<Categoria> categorieRisultanti=MyServiceFactory.getCategoriaServiceInstance().
						ricercaCategoria(pezziComponentiInput); //TODO: implementa ricercaCategoria in service e in DAO. 			
				if (categorieRisultanti==null) {
					request.setAttribute("dangerMessage","La ricerca non ha prodotto risultati");
					request.getRequestDispatcher("formRicercaAvanzataCategoria.jsp").forward(request,response);
					return;
				} else {
					request.setAttribute("listaCategorieAttribute",categorieRisultanti);
					request.getRequestDispatcher("categoria/elencoCategorie.jsp").forward(request,response);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		}
	}

}
