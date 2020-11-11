<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="it">
<head>
	<jsp:include page="../header.jsp" />
	<title>Elimina categoria</title>
	
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
		        <h5>Quale categoria vuoi eliminare?</h5> 
		    </div>
		    <div class='card-body'>

					<form method="post" action="${pageContext.request.contextPath}/PrepareDeleteCategoriaDaDropdownServlet" novalidate="novalidate">
					
						<div class="form-row">
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