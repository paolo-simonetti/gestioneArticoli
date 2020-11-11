package it.gestionearticoli.web.servlet.utente;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.gestionearticoli.model.Utente;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/ExecuteCambiaRuoloServlet")
public class ExecuteCambiaRuoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteCambiaRuoloServlet() {
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
		Long idUtente=Long.parseLong(request.getParameter("idUtente"));
		String ruoloSelezionato=request.getParameter("ruoloSelezionato");
		try {
			Utente utente=MyServiceFactory.getUtenteServiceInstance().trovaTramiteId(idUtente);
			Utente.Ruolo.conversioneRuolo.put("admin",Utente.Ruolo.ADMIN);
			Utente.Ruolo.conversioneRuolo.put("op",Utente.Ruolo.OP);
			Utente.Ruolo.conversioneRuolo.put("guest",Utente.Ruolo.GUEST);
			utente.setRuolo(Utente.Ruolo.conversioneRuolo.get(ruoloSelezionato));
			MyServiceFactory.getUtenteServiceInstance().aggiorna(utente);
			TreeSet<Utente> listaUtenti=MyServiceFactory.getUtenteServiceInstance().listAll();
			request.setAttribute("listaUtentiAttribute",listaUtenti);
			request.setAttribute("successMessage","Permessi aggiornati con successo!");			
			request.getRequestDispatcher("utente/elencoUtenti.jsp").forward(request,response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
