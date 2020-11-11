<!-- navbar -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-primary">

	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
       <span class="navbar-toggler-icon"></span>
     </button>
  
  <c:if test="${sessionScope.ruoloUtente eq 'op'}" var="isOP" scope="session"/>
  <c:if test="${sessionScope.ruoloUtente eq 'guest'}" var="isGuest" scope="session"/>
  <c:if test="${sessionScope.ruoloUtente eq 'admin'}" var="isAdmin" scope="session"/>
   
  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/menu.jsp">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Articoli</a>
        <div class="dropdown-menu" aria-labelledby="dropdown01">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/ListArticoliServlet">Elenco articoli</a>
          <c:if test="${sessionScope.isAdmin eq 'true' or pageScope.isOP eq 'true'}">
            <a class="dropdown-item" href="${pageContext.request.contextPath}/PrepareInsertArticoloServlet">Inserisci nuovo articolo</a>
            <a class="dropdown-item" href="${pageContext.request.contextPath}/articolo/updateDaDropdownArticolo.jsp">Aggiorna articolo</a>
    	 </c:if>	
         <c:if test="${sessionScope.isAdmin eq 'true'}"><a class="dropdown-item" href="${pageContext.request.contextPath}/PrePrepareDeleteCategoriaDaDropdownServlet">Elimina articolo</a></c:if>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/articolo/getDaDropdownArticolo.jsp">Visualizza articolo</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/articolo/formRicercaAvanzataArticolo.jsp">Ricerca avanzata di articolo</a>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown02" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Categorie</a>
        <div class="dropdown-menu" aria-labelledby="dropdown02">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/ListCategorieServlet">Elenco categorie</a> 
          <c:if test="${sessionScope.isAdmin eq 'true' or pageScope.isOP eq 'true'}">
            <a class="dropdown-item" href="${pageContext.request.contextPath}/categoria/insertCategoria.jsp">Inserisci nuova categoria</a>
            <a class="dropdown-item" href="${pageContext.request.contextPath}/PrePrepareUpdateCategoriaDaDropdownServlet">Aggiorna categoria</a>
          </c:if>
          <c:if test="${sessionScope.isAdmin eq 'true'}"><a class="dropdown-item" href="${pageContext.request.contextPath}/categoria/deleteDaDropdownCategoria.jsp">Elimina categoria</a></c:if>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/categoria/getDaDropdownCategoria.jsp">Visualizza una categoria specifica</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/categoria/formRicercaAvanzataCategoria.jsp">Ricerca avanzata di categoria</a>
        </div>
      </li>
	  <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown03" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Benvenuto, ${sessionScope.nomeUtente}</a>
        <div class="dropdown-menu" aria-labelledby="dropdown03">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
          <c:if test="${sessionScope.isAdmin eq 'true'}" > <a class="dropdown-item" href="${pageContext.request.contextPath}/ListUtentiServlet">Visualizza elenco utenti</a> </c:if>
        </div>
      </li>
	      
    </ul>
    
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>
