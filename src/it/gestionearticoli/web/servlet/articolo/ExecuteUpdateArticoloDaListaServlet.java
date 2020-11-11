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
@WebServlet("/ExecuteUpdateArticoloDaListaServlet")
public class ExecuteUpdateArticoloDaListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ExecuteUpdateArticoloDaListaServlet() {
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
		String idStringaInput=request.getParameter("idArticolo");
		Long idInput=Long.parseLong(idStringaInput);
		String codiceInput=request.getParameter("codice");
		String descrizioneInput=request.getParameter("descrizione");
		String prezzoStringaInput=request.getParameter("prezzo");
		String categoriaStringaInput=request.getParameter("categoria");
		System.out.println(categoriaStringaInput);
		Long categoriaFK=(categoriaStringaInput.isEmpty()? 0:Long.parseLong(categoriaStringaInput));
		int prezzo=Integer.parseInt(prezzoStringaInput);
		if (codiceInput.isEmpty()) {
			request.setAttribute("errorMessage", "Attenzione: sono presenti errori di validazione");
			Articolo articolo=new Articolo();
			try {
				articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idInput);				
			} catch(Exception e) {
				e.printStackTrace();
			}
			articolo.setCodice(codiceInput);
			request.setAttribute("articoloDaAggiornare",articolo);
			request.getRequestDispatcher("articolo/update.jsp").forward(request, response);
			return;
		} else if (descrizioneInput.isEmpty()) {
			request.setAttribute("errorMessage", "Attenzione: sono presenti errori di validazione");
			Articolo articolo=new Articolo();
			try {
				articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idInput);				
			} catch(Exception e) {
				e.printStackTrace();
			}
			articolo.setDescrizione(descrizioneInput);
			request.setAttribute("articoloDaAggiornare",articolo);
			request.getRequestDispatcher("articolo/update.jsp").forward(request, response);
			return;
		} else if (prezzo<1) {
			request.setAttribute("errorMessage", "Attenzione: sono presenti errori di validazione");
			Articolo articolo=new Articolo();
			try {
				articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idInput);				
			} catch(Exception e) {
				e.printStackTrace();
			}
			articolo.setPrezzo(prezzo);
			request.setAttribute("articoloDaAggiornare",articolo);
			request.getRequestDispatcher("articolo/update.jsp").forward(request, response);
			return;
		} else if(categoriaFK<1) {
			request.setAttribute("errorMessage", "Attenzione: sono presenti errori di validazione");
			Articolo articolo=new Articolo();
			try {
				articolo=MyServiceFactory.getArticoloServiceInstance().trovaTramiteId(idInput);				
			} catch(Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("articoloDaAggiornare",articolo);
			request.getRequestDispatcher("articolo/update.jsp").forward(request, response);
			return;
		}
		// controllo se la categoria impostata è presente
		TreeSet<Articolo> articoliPresenti=new TreeSet<>();
		try {
			TreeSet<Categoria> categoriePresenti=MyServiceFactory.getCategoriaServiceInstance().listAll();
			boolean categoriaIsPresente=false;
			for (Categoria c:categoriePresenti) {
				if (categoriaFK==c.getIdCategoria()) {
					categoriaIsPresente=true;
					break;
				}
			}
			Articolo articolo=new Articolo(codiceInput,descrizioneInput,prezzo,null);
			articolo.setId(idInput);
			if (categoriaIsPresente) {
				articolo.setCategoriaDiAfferenza(MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(categoriaFK));
				MyServiceFactory.getArticoloServiceInstance().aggiorna(articolo);
				request.setAttribute("successMessage", "Operazione effettuata con successo");
				articoliPresenti=MyServiceFactory.getArticoloServiceInstance().listAll();
			} else {
				articoliPresenti=MyServiceFactory.getArticoloServiceInstance().listAll();
				request.setAttribute("alertMessage","La categoria impostata era inesistente, quindi è stata lasciata quella originaria");
			}			
		} catch (Exception e) {
			System.err.println("Errore nel recupero delle categorie presenti!");
			e.printStackTrace();
		} finally {
			request.setAttribute("listaArticoliAttribute", articoliPresenti);
			request.getRequestDispatcher("articolo/results.jsp").forward(request, response);
		}
	}

}
