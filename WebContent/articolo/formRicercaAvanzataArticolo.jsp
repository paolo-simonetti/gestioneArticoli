<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Ricerca avanzata di articolo</title>
	
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

					<form method="post" action="${pageContext.request.contextPath}/RicercaArticoloServlet" novalidate="novalidate">
					
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Codice</label>
								<input type="text" name="codice" id="codice" class="form-control">
							</div>
							
							<div class="form-group col-md-6">
								<label>Descrizione</label>
								<input type="text" name="descrizione" id="descrizione" class="form-control">
							</div>
						</div>
						
						<div class="form-row">	
							<div class="form-group col-md-3">
								<label>Prezzo</label>
								<input type="number" class="form-control" name="prezzo" id="prezzo">
							</div>		
							<div class="form-group col-md-3">
								<label>Categoria</label>
								<input type="text" class="form-control" name="categoria" id="categoria">
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