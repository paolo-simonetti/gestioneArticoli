<%@page import="it.gestionearticoli.model.Articolo"%>
<%@page import="java.util.List"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Pagina dei risultati</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    
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
		<div class="alert alert-warning alert-dismissible fade show ${alertMessage==null?'d-none': ''}" role="alert">
		  ${alertMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="alert alert-danger alert-dismissible fade show ${dangerMessage==null?'d-none': ''}" role="alert">
		  ${dangerMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Lista dei risultati</h5> 
		    </div>
		    <div class='card-body'>
		    	<c:if test="${sessionScope.isAdmin eq 'true' or sessionScope.isOP eq 'true'}"><a class="btn btn-primary " href="PrepareInsertArticoloServlet">Add New</a></c:if>
		    
		        <div class='table-responsive'>
		            <table class='table table-striped ' >
		                <thead>
		                    <tr>
		                        <th>Id</th>
		                        <th>Codice</th>
		                        <th>Descrizione</th>
		                        <th>Prezzo</th>
		                        <th>Categoria</th>
		                        <th>Azioni</th>
		                    </tr>
		                </thead>
		                <tbody>
		                  	<c:forEach items="${requestScope.listaArticoliAttribute}" var="item">
		                	  <tr >
		                        <td><c:out value="${item.id}"></c:out></td>
		                        <td><c:out value="${item.codice}"></c:out></td>
		                        <td><c:out value="${item.descrizione}"></c:out></td>
		                        <td><c:out value="${item.prezzo}"></c:out></td>
		                        <td><c:out value="${item.categoriaDiAfferenza.nomeCategoria}"></c:out></td>
		                         <td>
									<a class="btn  btn-sm btn-outline-secondary" href="GetArticoloDaListaServlet?idArticoloDaVisualizzare=${item.id}">Visualizza articolo</a>
									<c:if test="${sessionScope.isAdmin eq 'true' or sessionScope.isOP eq 'true'}"><a class="btn  btn-sm btn-outline-primary ml-2 mr-2" href="PrepareUpdateArticoloDaListaServlet?idArticoloDaAggiornare=${item.id}">Edit</a></c:if>
									<c:if test="${sessionScope.isAdmin eq 'true'}"><a class="btn btn-outline-danger btn-sm" href="PrepareDeleteArticoloDaListaServlet?idArticoloDaEliminare=${item.id}">Delete</a></c:if>
									<a class="btn  btn-sm btn-outline-info" href="GetCategoriaDaListaServlet?idCategoria=${item.categoriaDiAfferenza.idCategoria}">Visualizza categoria</a>
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