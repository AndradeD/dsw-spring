<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents">

    <!-- Campos básicos -->
	<form:form action="${pageContext.request.contextPath}/edital/prova/save" commandName="form" method="POST" enctype="utf8" role="form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<form:hidden path="idEdital"/>
		<form:hidden path="codigoOriginal"/>
	   	
	   	<div class="mdl-grid">
	        <div class="mdl-cell mdl-cell--1-col">
	        </div>
	        
	        <div class="mdl-cell mdl-cell--10-col">
			    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
			        <!-- Form header -->
			        <div class="mdl-card__title">
			            <h2 class="mdl-card__title-text">
		            			<spring:message code="edital.form.titulo.prova.edicao"/>
			            </h2>
			        </div>
			
			        <!-- Form body -->
				    <div class="mdl-card__supporting-text">
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="codigo" id="prova-codigo" class="mdl-textfield__input" type="text" value="${form.codigo}"/>
			                <form:errors id="error-codigo" path="codigo" cssClass="error-block"/>				            
						    <label class="mdl-textfield__label" for="prova-codigo"><spring:message code="edital.form.label.codigo"/>:</label>
						</div>
	
						<div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="nome" id="prova-nome" class="mdl-textfield__input" type="text" value="${form.nome}"/>
			                <form:errors id="error-nome" path="nome" cssClass="error-block"/>				            
						    <label class="mdl-textfield__label" for="prova-nome"><spring:message code="edital.form.label.nome"/>:</label>
						</div>
	
						<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						    <form:input path="notaMinimaAprovacao" id="prova-notaMinima" class="mdl-textfield__input" type="number" pattern="[0-9]+" value="${form.notaMinimaAprovacao}"/>
		                    <form:errors id="error-notaMinima" path="notaMinimaAprovacao" cssClass="error-block"/>
						    <label class="mdl-textfield__label" for="prova-notaMinima"><spring:message code="edital.form.label.nota.minima.prova"/>:</label>
						</div>

						<label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="prova-dispensavel">
							<form:checkbox path="dispensavel" id="prova-dispensavel" class="mdl-checkbox__input"/>
							<span class="mdl-checkbox__label"><spring:message code="edital.form.dialog.label.dispensavel"/></span>
						</label>
						
						<div style="margin-top: 32px;">
							<div class="left">
								<h4 class="list-title">
									<spring:message code="edital.form.dialog.titulo.pesos.questoes"/>
								</h4>
							</div>
							<div class="right">
								<button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" type="button" onclick="javascript:adicionaQuestao()">
									<spring:message code="edital.form.dialog.botao.nova.questao"/>
								</button>
							</div>
						</div>
						
						<table id="tbPesos" class="wide mdl-data-table mdl-js-data-table mdl-shadow--2dp">
						<tbody>
						<c:forEach var="peso" varStatus="status" items="${form.pesosQuestoes}">
						<tr>
							<td class="mdl-data-table__cell--non-numeric">
							    <input name="pesosQuestoes" class="editor-peso-prova" type="number" pattern="[0-9]+" value="${peso}"/>
			                    <form:errors id="error-notaMinima" path="pesosQuestoes" cssClass="error-block"/>
							</td>
							<td>
								<c:if test="${status.index > 0}">
									<button type="button" class="mdl-button mdl-js-button mdl-button--icon" onclick="javascript:removeQuestao(event)">
										<i class="material-icons">delete</i>
									</button>
								</c:if>
							</td>
						</tr>
						</c:forEach>
						</tbody>
						</table>
					</div>
					
			        <!-- Form buttons -->
			        <div class="mdl-card__actions mdl-card--border">
			       	 	<div class="left">
			            </div>
			            <div class="right">
							<button type="submit" class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
								<spring:message code="edital.form.botao.salva"/>
							</button>
			            		<a href="${pageContext.request.contextPath}/edital/edit/${form.idEdital}">
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

<script>
function adicionaQuestao() {
	var input = angular.element('<input name="pesosQuestoes" class="editor-peso-prova" type="number" pattern="[0-9]+"/>');
	
	var td1 = angular.element('<td class="mdl-data-table__cell--non-numeric">')
		.append(input);

	var button = angular.element('<button type="button" class="mdl-button mdl-js-button mdl-button--icon" onclick="javascript:removeQuestao(event)"><i class="material-icons">delete</i></button>');

	var td2 = angular.element('<td>')
		.append(button);
	
	var tr = angular.element('<tr>')
		.append(td1)
		.append(td2);

	angular.element(document.querySelector('#tbPesos > tbody'))
		.append(tr);
}

function removeQuestao(event) {
    var target = angular.element(event.target);
    target.parent().parent().parent().remove();
}
</script>