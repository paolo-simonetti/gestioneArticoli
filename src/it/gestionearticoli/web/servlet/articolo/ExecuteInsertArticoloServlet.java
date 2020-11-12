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

@WebServlet("/ExecuteInsertArticoloServlet")
public class ExecuteInsertArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteInsertArticoloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("permessiMancantiMessage","E' stato rilevato un tentativo di cambiare la tipologia di richiesta al server.");
		request.getRequestDispatcher("welcome.jsp").forward(request,response);
		HttpSession session=request.getSession();
		session.invalidate();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		HttpSession session=request.getSession();
		if (((String) session.getAttribute("nomeUtente"))==null||((String) session.getAttribute("cognomeUtente"))==null||
				((String) session.getAttribute("codiceFiscaleUtente"))==null||session.getAttribute("ruoloUtente").equals("guest")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		String codiceInputParam = request.getParameter("codice");
		String descrizioneInputParam = request.getParameter("descrizione");
		String prezzoInputStringParam = request.getParameter("prezzo");
		Integer prezzo = !prezzoInputStringParam.isEmpty() ? Integer.parseInt(prezzoInputStringParam) : 0;
		String categoriaInputStringParam=request.getParameter("categoria");
		// se la validazione fallisce torno in pagina
		TreeSet<Articolo> articoliPresenti=null;
		try {
			articoliPresenti=MyServiceFactory.getArticoloServiceInstance().listAll();
		} catch(Exception e) {
			request.setAttribute("dangerMessage","Errore nel recupero degli articoli presenti sul sito.");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
			e.printStackTrace();
			return;
		}
		for (Articolo a:articoliPresenti) {
			if (descrizioneInputParam.equals(a.getDescrizione())) {
				request.setAttribute("errorMessage", "Un articolo con questa descrizione è già presente. Volevi farne l'update?");
				request.getRequestDispatcher("articolo/insert.jsp").forward(request, response);
				return;
			}
		}
		if (codiceInputParam.isEmpty() || descrizioneInputParam.isEmpty() || prezzo < 1) {
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("articolo/insert.jsp").forward(request, response);
			return;
		}
		
		/* se la categoria non è ancora presente, l'articolo viene inserito ugualmente, ma categorizzato a null, perché 
		è così che ho impostato il model*/
		try {
			TreeSet<Categoria> categoriePresenti=MyServiceFactory.getCategoriaServiceInstance().listAll();
			boolean categoriaIsPresente=false;
			Long idCategoria=null;
			for (Categoria c:categoriePresenti) {
				if (categoriaInputStringParam.equals(c.getNomeCategoria())) {
					categoriaIsPresente=true;
					idCategoria=c.getIdCategoria();
					break;
				}
			}
			Articolo articoloDaInserire=new Articolo(codiceInputParam,descrizioneInputParam,prezzo,null);
			if (!categoriaIsPresente) {
				MyServiceFactory.getArticoloServiceInstance().inserisciNuovo(articoloDaInserire);
				if (categoriaInputStringParam.isEmpty()) {
					request.setAttribute("successMessage","Articolo aggiunto con successo!");
				} else {
					request.setAttribute("alertMessage","La categoria inserita non era presente nella lista, è stata impostata a null!");					
				}
				request.setAttribute("listaArticoliAttribute",MyServiceFactory.getArticoloServiceInstance().listAll());
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
			} else {
				articoloDaInserire.setCategoriaDiAfferenza(MyServiceFactory.getCategoriaServiceInstance().trovaTramiteId(idCategoria));
				MyServiceFactory.getArticoloServiceInstance().inserisciNuovo(articoloDaInserire);
				request.setAttribute("listaArticoliAttribute",MyServiceFactory.getArticoloServiceInstance().listAll());
				request.setAttribute("successMessage", "Operazione effettuata con successo");
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response); 				
			}
		} catch (Exception e) {
			request.setAttribute("dangerMessage","Errore nell'inserimento dell'articolo.");
			request.getRequestDispatcher("menu.jsp").forward(request,response);
			e.printStackTrace();
		}

	}

}
