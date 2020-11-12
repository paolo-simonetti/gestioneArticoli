package it.gestionearticoli.web.servlet.articolo;

import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.model.Articolo;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/RicercaArticoloServlet")
public class RicercaArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RicercaArticoloServlet() {
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
		String codiceInputParam=request.getParameter("codice");
		String descrizioneInputParam=request.getParameter("descrizione");
		String prezzoStringInputParam=request.getParameter("prezzo");
		String categoriaInputParam=request.getParameter("categoria");
		//valido gli input di ricerca
		Integer prezzoInputParam=null;
		if (prezzoStringInputParam!=null &&!prezzoStringInputParam.isEmpty()) {
			prezzoInputParam=Integer.parseInt(prezzoStringInputParam);
		}
		if (prezzoInputParam!=null && prezzoInputParam<=0) {
			request.setAttribute("errorMessage","Impostare come prezzo un numero positivo, per sperare di avere risultati");
			request.getRequestDispatcher("articolo/formRicercaAvanzataArticolo.jsp");
			return;
		}
		// se ho lasciato bianchi tutti i campi di ricerca, voglio semplicemente tutta la lista di articoli
		if ((codiceInputParam==null||codiceInputParam.isEmpty())&&(descrizioneInputParam==null||descrizioneInputParam.isEmpty())&&
				(prezzoStringInputParam==null||prezzoStringInputParam.isEmpty())&&(categoriaInputParam==null||categoriaInputParam.isEmpty())) { 
			try {
				request.setAttribute("listaArticoliAttribute",MyServiceFactory.getArticoloServiceInstance().listAll());
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
				return;
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("dangerMessage","Errore nel reperimento degli articoli presenti.");
				request.getRequestDispatcher("menu.jsp").forward(request,response);
				return;
			}
		} else {
			/*Definisco una mappa le cui chiavi sono i campi di ricerca non nulli, e i valori sono gli array di stringhe 
			  componenti i singoli campi */
			TreeMap<String,TreeSet<String>> inputRicerca=new TreeMap<>();
			if (codiceInputParam!=null&&!codiceInputParam.isEmpty()) {
				// se il codice è un campo di ricerca non vuoto, separo la stringa nelle parole che la compongono
				String[] pezziComponentiCodiceInputParam=codiceInputParam.trim().split("\\s+");
				TreeSet<String> pezziComponentiCodice=new TreeSet<>();
				for (int i=0; i<pezziComponentiCodiceInputParam.length; i++) {
					if (!(pezziComponentiCodiceInputParam[i].isEmpty())) { // mi curo di eliminare eventuali elementi rimasti vuoti
						pezziComponentiCodice.add(pezziComponentiCodiceInputParam[i]);
					} 
				}
				inputRicerca.put("CODICE",pezziComponentiCodice); /* chiamo le chiavi con lo stesso nome delle colonne del database, per
				motivi che si vedono in ArticoloDAOImpl */ 
			}
			if (descrizioneInputParam!=null&&!descrizioneInputParam.isEmpty()) {
				String[] pezziComponentiDescrizioneInputParam=descrizioneInputParam.trim().split("\\s+");
				TreeSet<String> pezziComponentiDescrizione=new TreeSet<>();
				for (int i=0; i<pezziComponentiDescrizioneInputParam.length; i++) {
					if (!pezziComponentiDescrizioneInputParam[i].isEmpty()) {
						pezziComponentiDescrizione.add(pezziComponentiDescrizioneInputParam[i]);
					} 
				}
				inputRicerca.put("DESCRIZIONE",pezziComponentiDescrizione);
			}
			if (prezzoStringInputParam!=null&&!prezzoStringInputParam.isEmpty()) {
				//Non posso buttare direttamente la stringa del prezzo nella mappa
				TreeSet<String> setMonoElemento=new TreeSet<>();
				setMonoElemento.add(prezzoStringInputParam);
				inputRicerca.put("PREZZO",setMonoElemento);
			}
			if (categoriaInputParam!=null&&!categoriaInputParam.isEmpty()) {
				String[] pezziComponentiCategoriaInputParam=categoriaInputParam.trim().split("\\s+");
				TreeSet<String> pezziComponentiCategoria=new TreeSet<>();
				for (int i=0; i<pezziComponentiCategoriaInputParam.length; i++) {
					if (!pezziComponentiCategoriaInputParam[i].isEmpty()) {
						pezziComponentiCategoria.add(pezziComponentiCategoriaInputParam[i]);
					} 
				}
				inputRicerca.put("NOME_CATEGORIA",pezziComponentiCategoria);
			}
			try {
				TreeSet<Articolo> articoliRisultanti=MyServiceFactory.getArticoloServiceInstance().ricercaArticolo(inputRicerca);
				request.setAttribute("listaArticoliAttribute",articoliRisultanti);
				request.getRequestDispatcher("articolo/results.jsp").forward(request,response);
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("dangerMessage","Errore nell'esecuzione della ricerca.");
				request.getRequestDispatcher("menu.jsp").forward(request,response);
			} 
		}
				
	}

}
