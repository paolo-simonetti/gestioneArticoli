package it.gestionearticoli.web.servlet;

import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticoli.model.Utente;
import it.gestionearticoli.service.MyServiceFactory;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		TreeSet<Utente> listaIscritti=null;
		try {
			listaIscritti= MyServiceFactory.getUtenteServiceInstance().listAll();			
		} catch(Exception e) {
			System.err.println("Problemi nel recupero degli utenti iscritti");
			e.printStackTrace();
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
			return;
		}
		boolean utenteIsRegistrato=false;
		Utente utenteIdentificato=null;
		for (Utente u:listaIscritti) {
			if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
				utenteIsRegistrato=true;
				utenteIdentificato=u;
				break;
			}
		}
		if (utenteIsRegistrato) {
			HttpSession session=request.getSession(true);
			session.setAttribute("nomeUtente",utenteIdentificato.getNome());
			session.setAttribute("cognomeUtente",utenteIdentificato.getCognome());
			session.setAttribute("codiceFiscaleUtente",utenteIdentificato.getCodiceFiscale());
			session.setAttribute("ruoloUtente",utenteIdentificato.getRuolo().toString());
			request.getRequestDispatcher("menu.jsp").forward(request,response);
		} else {
			request.setAttribute("credenzialiErrateMessage","Le credenziali inserite sono errate o non sei registrato");
			request.getRequestDispatcher("welcome.jsp").forward(request,response);
		}
		
	}

}
