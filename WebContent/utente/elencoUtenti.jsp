<%@page import="it.gestionearticoli.model.Categoria"%>
<%@page import="java.util.TreeSet"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Elenco degli utenti iscritti</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="../assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="../navbar.jsp" />
	
	<main role="main" class="container">
	
		<div class="alert alert-success alert-dismissible fade show ${successMessage==null?'d-none': ''}" role="alert">
		  ${successMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Lista degli utenti iscritti</h5> 
		    </div>
		    <div class='card-body'>
		    
		        <div class='table-responsive'>
		            <table class='table table-striped ' >
		                <thead>
		                    <tr>
		                        <th>Id Utente</th>
		                        <th>Nome</th>
		                        <th>Cognome</th>
		                        <th>Codice fiscale</th>
		                        <th>Ruolo</th>
		                        <th>Azioni</th>
		                    </tr>
		                </thead>
		                <tbody>
		                  	<c:forEach items="${requestScope.listaUtentiAttribute}" var="item">
		                	  <tr >
		                        <td><c:out value="${item.idUtente}"/></td>
		                        <td><c:out value="${item.nome}"/></td>
		                        <td><c:out value="${item.cognome}"/></td>
		                        <td><c:out value="${item.codiceFiscale}"/></td>
		                        <td><c:out value="${item.ruolo}"/></td>
		                        <td>
									<c:if test="${sessionScope.isAdmin eq 'true'}"><a class="btn btn-outline-danger btn-sm" href="PrepareCambiaRuoloServlet?idUtente=${item.idUtente}">Cambia permessi</a></c:if>
								</td>
		                      </tr>
		                	</c:forEach>		                   
		                </tbody>
		            </table>
		        </div>
		   
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	
	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>