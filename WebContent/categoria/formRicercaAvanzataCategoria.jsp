<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Ricerca avanzata di categoria</title>
	
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
		        <h5>Inserisci campi di ricerca</h5> 
		    </div>
		    <div class='card-body'>

					<form method="post" action="${pageContext.request.contextPath}/RicercaCategoriaServlet" novalidate="novalidate">
					
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Nome della categoria </label>
								<input type="text" name="nomeCategoria" id="nomeCategoria" class="form-control">
							</div>
						</div>
							
						<button type="submit" name="ricercaCategoria" value="ricercaCategoria" id="ricercaCategoria" class="btn btn-primary">Conferma</button>
					

					</form>

		    
		    
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>