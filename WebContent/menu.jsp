<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="it">
  <head>
    
    <jsp:include page="header.jsp" />
    
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    <style type="text/css">
    	body {
		  padding-top: 3.5rem;
		}	
    </style>
    
    <title>Gestione degli Articoli!</title>
  </head>
  <body>
  
	<jsp:include page="navbar.jsp"></jsp:include>
  
  
	<main role="main">

	  <!-- Main jumbotron for a primary marketing message or call to action -->
	  <div class="jumbotron" >
	    <div class="container">
	      <h1 class="display-3">Benvenuto alla Gestione!</h1>
	      <p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
	      <p><a class="btn btn-primary btn-lg" href="ListArticoliServlet" role="button">Vai alla Lista di Articoli &raquo;</a></p>
          <p><a class="btn btn-primary btn-lg" href="ListCategorieServlet" role="button">Vai alla Lista delle Categorie &raquo;</a></p>
          <c:if test="${sessionScope.isAdmin eq 'true'}"><a class="btn btn-primary btn-lg" href="ListUtentiServlet" role="button">Vai alla Lista degli Utenti &raquo;</a> </c:if>
	    </div>
	  </div>
	  
	  <div class="container">
	    <!-- Example row of columns -->
	    <div class="row">
	      <div class="col-md-4">
	        <h2>Heading</h2>
	        <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
	        <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
	      </div>
	      <div class="col-md-4">
	        <h2>Heading</h2>
	        <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
	        <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
	      </div>
	      <div class="col-md-4">
	        <h2>Heading</h2>
	        <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
	        <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
	      </div>
	    </div>
	
	    <hr>
	
	  </div> <!-- /container -->
	
	</main>
	
	<jsp:include page="footer.jsp" />
  </body>
</html>