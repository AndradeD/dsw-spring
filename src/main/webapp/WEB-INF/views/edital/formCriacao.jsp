<<<<<<< HEAD
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">	
	<form:form action="${pageContext.request.contextPath}/edital/create" commandName="form" method="POST" enctype="utf8" role="form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<form:hidden path="id"/>
	   	
       	<!-- Campos básicos -->
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--1-col">
	        </div>
	        
	        <div class="mdl-cell mdl-cell--10-col">
			    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
			        <!-- Form header -->
			        <div class="mdl-card__title">
			            <h2 class="mdl-card__title-text">
		            			<spring:message code="edital.form.titulo.criacao"/>
			            </h2>
			        </div>
			
			        <!-- Form body -->
				    <div class="mdl-card__supporting-text">
						<div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="nome" id="edital-nome" class="mdl-textfield__input" type="text" value="${form.nome}"/>
			                <form:errors id="error-nome" path="nome" cssClass="error-block"/>				            
						    <label class="mdl-textfield__label" for="edital-nome"><spring:message code="edital.form.label.nome"/>:</label>
						</div>
	
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="notaMinimaAlinhamento" id="edital-notaMinima" class="mdl-textfield__input" type="number" pattern="[0-9]+"/>
		                    <form:errors id="error-notaMinima" path="notaMinimaAlinhamento" cssClass="error-block"/>
						    <label class="mdl-textfield__label" for="edital-notaMinima"><spring:message code="edital.form.label.nota.minima.alinhamento"/>:</label>
						</div>
					</div>
					
			        <!-- Form buttons -->
			        <div class="mdl-card__actions mdl-card--border">
			       	 	<div class="left">
			            </div>
			            <div class="right">
							<button type="submit" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
								<spring:message code="edital.form.botao.ok"/>
							</button>
			            		<a href="${pageContext.request.contextPath}/edital/list">
								<button type="button" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
									<spring:message code="edital.form.botao.cancela"/>
								</button>
							</a>
					    </div>
			        </div>
				</div>
			</div>

	        <div class="mdl-cell mdl-cell--1-col">
	        </div>	        
		</div>
	</form:form>
</div>
=======
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">	
	<form:form action="${pageContext.request.contextPath}/edital/create" commandName="form" method="POST" enctype="utf8" role="form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<form:hidden path="id"/>
	   	
       	<!-- Campos básicos -->
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--1-col">
	        </div>
	        
	        <div class="mdl-cell mdl-cell--10-col">
			    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
			        <!-- Form header -->
			        <div class="mdl-card__title">
			            <h2 class="mdl-card__title-text">
		            			<spring:message code="edital.form.titulo.criacao"/>
			            </h2>
			        </div>
			
			        <!-- Form body -->
				    <div class="mdl-card__supporting-text">
						<div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="nome" id="edital-nome" class="mdl-textfield__input" type="text" value="${form.nome}"/>
			                <form:errors id="error-nome" path="nome" cssClass="error-block"/>				            
						    <label class="mdl-textfield__label" for="edital-nome"><spring:message code="edital.form.label.nome"/>:</label>
						</div>
	
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="notaMinimaAlinhamento" id="edital-notaMinima" class="mdl-textfield__input" type="number" pattern="[0-9]+"/>
		                    <form:errors id="error-notaMinima" path="notaMinimaAlinhamento" cssClass="error-block"/>
						    <label class="mdl-textfield__label" for="edital-notaMinima"><spring:message code="edital.form.label.nota.minima.alinhamento"/>:</label>
						</div>
					</div>
					
			        <!-- Form buttons -->
			        <div class="mdl-card__actions mdl-card--border">
			       	 	<div class="left">
			            </div>
			            <div class="right">
							<button type="submit" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
								<spring:message code="edital.form.botao.ok"/>
							</button>
			            		<a href="${pageContext.request.contextPath}/edital/list">
								<button type="button" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
									<spring:message code="edital.form.botao.cancela"/>
								</button>
							</a>
					    </div>
			        </div>
				</div>
			</div>

	        <div class="mdl-cell mdl-cell--1-col">
	        </div>	        
		</div>
	</form:form>
</div>
>>>>>>> 52b3db79771b142471ce1fa8934f3ebb9506a314
