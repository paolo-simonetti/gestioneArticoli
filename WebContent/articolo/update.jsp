<%@page import="it.gestionearticoli.model.Articolo"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Aggiorna articolo</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="../navbar.jsp" />
	
	<main role="main" class="container">
			
		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': 
		''}" role="alert">
		  ${errorMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Aggiorna articolo</h5> 
		    </div>
		    <div class='card-body'>

					<h6 class="card-title">I campi con <span class="text-danger">*</span> 
					sono obbligatori</h6>

					<form method="post" action="ExecuteUpdateArticoloDaListaServlet" 
					novalidate="novalidate">
						<div>
							<div class="form-row">
							<div class="form-group col-md-6">
								<label> </label>
								<input type="hidden" name="idArticolo" id="idArticolo" class="form-control" value="${requestScope.articoloDaAggiornare.id}">
							</div>
							</div>
						</div>	
	
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Codice <span class="text-danger">*</span></label>
								<input type="text" name="codice" id="codice" 
								class="form-control" value="${requestScope.articoloDaAggiornare.codice}" placeholder="Inserisci un codice..." required>
							</div>
							
							<div class="form-group col-md-6">
								<label>Descrizione <span class="text-danger">*</span></label>
								<input type="text" name="descrizione" id="descrizione" class="form-control" value="${requestScope.articoloDaAggiornare.descrizione}" placeholder="Inserisci una descrizione..." required>
							</div>
						</div>
						
						<div class="form-row">	
							<div class="form-group col-md-3">
								<label>Prezzo <span class="text-danger">*</span></label>
								<input type="number" class="form-control" name="prezzo" id="prezzo" value="${requestScope.articoloDaAggiornare.prezzo}" placeholder="Inserisci un prezzo..." required>
							</div>
							
					        <div class="form-group">
  					      		<label for="selectCategoria">Categoria:</label>
  					      		<select class="form-control" id="selectCategoria" name="categoria">
    					    	<c:forEach items="${requestScope.listaCategorieAttribute}" var="categoriaDisponibile">
    					    		<option value="${categoriaDisponibile.idCategoria}">
    					    			${categoriaDisponibile.nomeCategoria}
    					    		</option>
    					    	</c:forEach>
  					      		</select>
					    	</div> 							
						</div>
							
						<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
					

					</form>

		    
		    
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>