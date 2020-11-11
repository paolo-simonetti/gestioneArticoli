<!doctype html>
<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Cambia permessi dell'utente</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="../assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="../navbar.jsp" />
	
	<main role="main" class="container">
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Cambia permessi dell'utente ${requestScope.utenteDaRiqualificare.nome} ${requestScope.utenteDaRiqualificare.cognome}</h5> 
		    </div>
		    <div class='card-body'>

		
					<form method="post" action="ExecuteCambiaRuoloServlet" novalidate="novalidate">
					  <div class="form-row">
						<div class="form-group col-md-6">
							<label> </label>
							<input type="hidden" name="idUtente" id="idUtente" class="form-control" value="${requestScope.utenteDaRiqualificare.idUtente}">
						</div>
					  </div>
					  
 					  <div class="form-group">
  					    <label for="selectRuolo">Nuovo ruolo:</label>
  					    <select class="form-control" id="selectRuolo" name="ruoloSelezionato">
    					  <option value='admin' >Admin</option>
    					  <option value='op'>OP</option>
    					  <option value='guest'>Guest</option>
  					    </select>
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