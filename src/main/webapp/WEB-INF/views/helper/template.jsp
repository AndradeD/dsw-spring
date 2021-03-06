<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="pt" ng-app="ppgiSelecaoApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Login">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>PPGI/UNIRIO</title>

	<!-- Fav Icon -->
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/static/img/favicon.ico" type="image/x-icon">
	<link rel="icon" href="${pageContext.request.contextPath}/static/img/favicon.ico" type="image/x-icon">

    <!-- Page styles -->
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" type="text/css" href="https://code.getmdl.io/1.2.1/material.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/third-party/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/third-party/twitter/css/bootstrap-social.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/styles.css">
</head>

<script>
var csrf = { name: "${_csrf.parameterName}", value: "${_csrf.token}" };
var contextPath = "${pageContext.request.contextPath}";
</script>

<body>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication var="user" property="principal" />
	</sec:authorize>

    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">

        <!-- Header -->
        <header class="mdl-layout__header" data-ng-controller="TopNavigatorController as navCtrl">
            <div class="mdl-layout__header-row">
                <!-- Title -->
                <span class="mdl-layout-title">
                    PPGI/UNIRIO: Sistema de seleção
                </span>


                <!-- Spacer -->
                <div class="mdl-layout-spacer">
                </div>


                <!-- Selected item -->
                <nav class="mdl-navigation">
					<sec:authorize access="isAuthenticated()">
	                		<div class="editalSelecionado">
	                			<div class="titulo">
	                				<spring:message code="template.titulo.edital.selecionado"/>:
	                			</div>
	                			<div>
			                		<c:if test="${empty sessionScope.edital}">
					                	<span class="mdl-button mdl-js-button mdl-js-ripple-effect edital-button" data-ng-click="navCtrl.abreJanelaSelecaoEdital()"><spring:message code="template.titulo.edital.nenhum"/></span>
					            </c:if>
			                		<c:if test="${not empty sessionScope.edital}">
					                	<span class="mdl-button mdl-js-button mdl-js-ripple-effect edital-button" data-ng-click="navCtrl.abreJanelaSelecaoEdital()">${sessionScope.edital.nome}</span>
					            </c:if>
					        </div>
	                		</div>
	                	</sec:authorize>
                </nav>


                <!-- Spacer -->
                <div class="mdl-layout-spacer">
                </div>


                <!-- Navigation -->
                <nav class="mdl-navigation">
					<sec:authorize access="isAuthenticated()">
	                    <div class="usuarioLogado">
	                    		<div class="cumprimento">
		                    		<spring:message code="template.label.ola"/>, <sec:authentication property="principal.nome"/>!
	                    		</div>
	                    		<div class="dataLogin">
		                    		<c:if test="${not empty user.dataUltimoLogin}">
		                    			Último login em ${user.dataUltimoLoginFormatada} 
		                    		</c:if>
	                    		</div>
	                    </div>
	                    <a class="mdl-button mdl-js-button mdl-js-ripple-effect header-button" href="${pageContext.request.contextPath}/login/change">
	                    		<spring:message code="template.comando.trocasenha"/>
	                    </a>
	                    <a class="mdl-button mdl-js-button mdl-js-ripple-effect header-button" href="${pageContext.request.contextPath}/logout">
	                    		<spring:message code="template.comando.logout"/>
	                    </a>
                    </sec:authorize>      
                </nav>
                

				<!-- Janela de selecao de edital -->
				<sec:authorize access="isAuthenticated()">
					<dialog class="mdl-dialog" style="width: 400px;">
					    <h4 class="mdl-dialog__title">
					    		<spring:message code="template.dialogo.seleciona.edital.titulo"/>
					    </h4>
					    <div class="mdl-dialog__content">
					      	<p>
					        		<spring:message code="template.dialogo.seleciona.edital.subtitulo"/>
					      	</p>
					      	<p>
					      		<select ng-model="navCtrl.editalSelecionado" class="wide" ng-options="edital.id as edital.nome for edital in navCtrl.editais" ng-init="navCtrl.editalSelecionado=${user.idEdital}">
								</select>
					      	</p>
					    	</div>
					    <div class="mdl-dialog__actions">
					      	<button type="button" class="mdl-button close">
					      		<spring:message code="template.dialogo.seleciona.edital.botao.cancela"/>
					      	</button>
					      	<button type="button" class="mdl-button" data-ng-click="navCtrl.selecionaEdital()">
					      		<spring:message code="template.dialogo.seleciona.edital.botao.ok"/>
							</button>
					    </div>
					</dialog>
				</sec:authorize> 
            </div>
        </header>


        <!-- Side bar -->
        <div id="sideBar" class="mdl-layout__drawer">
            <span class="mdl-layout-title">
                <spring:message code="template.titulo.opcoes"/>
            </span>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/list">
			    		<spring:message code="homepage.comandos.edital.registro"/>
			    	</a>
		   </sec:authorize>
		   <c:if test="${sessionScope.edital.status.codigo == 0}">
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/abertura">
			    		<spring:message code="homepage.comandos.edital.abre"/>
			    	</a>
		   </c:if>
		   <c:if test="${sessionScope.edital.status.codigo == 1}">
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/inscricao/encerramento">
			    		<spring:message code="homepage.comandos.edital.inscricoes.encerrar"/>
			    	</a>
		   </c:if>
		   <c:if test="${sessionScope.edital.status.codigo == 2}">
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/homologacao/inscricao/${sessionScope.edital.id}">
			    		<spring:message code="homepage.comandos.edital.inscricoes.homologar"/>
			    	</a>
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/homologacao/dispensa/${sessionScope.edital.id}">
			    		<spring:message code="homepage.comandos.edital.dispensas.homologar"/>
			    	</a>
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/homologacao/encerramento">
			    		<spring:message code="homepage.comandos.edital.homologacao.encerrar"/>
			    	</a>
		   </c:if>
		   <c:if test="${sessionScope.edital.status.codigo == 3}">
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/escrita/presenca">
			    		<spring:message code="homepage.comandos.edital.presenca.prova.escrita"/>
			    	</a>
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/escrita/notas">
			    		<spring:message code="homepage.comandos.edital.notas.prova.escrita"/>
			    	</a>
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/escrita/encerramento">
			    		<spring:message code="homepage.comandos.edital.encerrar.prova.escrita"/>
			    	</a>
		   </c:if>
		   <c:if test="${sessionScope.edital.status.codigo == 4}">
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/alinhamento/presenca">
			    		<spring:message code="homepage.comandos.edital.presenca.prova.oral"/>
			    	</a>
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/alinhamento/notas">
			    		<spring:message code="homepage.comandos.edital.notas.prova.alinhamento"/>
			    	</a>
			    <a class="sidebar-command" href="${pageContext.request.contextPath}/edital/alinhamento/encerramento">
			    		<spring:message code="homepage.comandos.edital.encerrar.prova.alinhamento"/>
			    	</a>
		   </c:if>
		   <c:if test="${sessionScope.edital.status.codigo == 5}">
		    		<span class="sidebar-no-command">
		    			<spring:message code="homepage.comandos.edital.nenhum.comando"/>
		    		</span>
		   </c:if>
        </div>


        <!-- Central stage -->
        <main id="centralStage" class="mdl-layout__content">
        </main>


        <!-- Footer -->
        <footer class="android-footer mdl-mega-footer">
            <div class="mdl-mega-footer--top-section">
                <div class="mdl-mega-footer--left-section">
                  <p class="mdl-typography--font-light">PPGI/UNIRIO: ©2017</p>
                </div>
            </div>
        </footer>
    </div>
  
  
  	<!-- Snackbar -->
	<div id="demo-snackbar-example" class="mdl-js-snackbar mdl-snackbar">
	  <div class="mdl-snackbar__text"><spring:message code='${param.message}'/></div>
	  <button class="mdl-snackbar__action" type="button"></button>
	</div>


    <!-- Material design -->
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-sanitize/1.6.6/angular-sanitize.js"></script>

	<!-- Components -->
	<script src="${pageContext.request.contextPath}/static/components/unirio/unirio.confirm.js"></script>
	<script src="${pageContext.request.contextPath}/static/third-party/ngTable/ng-table.min.js"></script>
	
	<!-- Navigator controller -->
	<script src="${pageContext.request.contextPath}/static/js/app.js"></script>
	<script src="${pageContext.request.contextPath}/static/components/translate/translate.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/helper/navigator.dataService.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/helper/navigator.controller.js"></script>

	<script>
	angular.element(document).ready(function () {
		/* Copia o conteudo para o painel central */
	    var centralStageElement = document.getElementById('centralStage');
	    var contentsElement = document.getElementById('contents');
	    centralStageElement.appendChild(contentsElement);
	});
	</script>

	<c:if test="${not empty param.message}">
		<script>
		window.onload = function () {
		    var snackbar = document.querySelector('#demo-snackbar-example');
		    var message = snackbar.getElementsByClassName("mdl-snackbar__text")[0].textContent;
		    snackbar.MaterialSnackbar.showSnackbar({ 
		    	message: message, 
		    	timeout: 20000,
		        actionHandler: function() { snackbar.MaterialSnackbar.cleanup_() },
		        actionText: 'x' 
		    });
        };
		</script>
	</c:if>
</body>
</html>