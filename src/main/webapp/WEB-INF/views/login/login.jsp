<%@include file="/WEB-INF/views/helper/template.jsp" %>

<div id="contents" data-ng-controller="LoginController">    
    <div class="mdl-grid">
        <div class="mdl-cell mdl-cell--3-col">
        </div>

        <div class="mdl-cell mdl-cell--6-col">

            <sec:authorize access="isAnonymous()">
				<form action="${pageContext.request.contextPath}/login/authenticate" method="POST" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				    
				    <div class="wide-card mdl-card mdl-shadow--2dp min-spacer-up">
				    
				        <!-- Form header -->
				        <div class="mdl-card__title">
				            <h2 class="mdl-card__title-text">
				            		<spring:message code="login.login.form.title"/>
				            </h2>
				        </div>
				
				        <!-- Form body -->
				        <div class="mdl-card__supporting-text">
				
				        <!-- Error message -->
				        <c:if test="${not empty error}">
					        <div class="alert alert-danger">
						        	<spring:message code="${error}"/>
					        </div>
				        </c:if>
				
				        <!-- E-mail field -->
				        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				            <input class="mdl-textfield__input" type="text" name="username" id="user-email">
				            <label class="mdl-textfield__label" for="user-email">
				            		<spring:message code="login.login.label.email"/>:
				            </label>
				        </div>
				
				        <!-- Password field -->
				        <div class="wide mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				            <input class="mdl-textfield__input" type="password" name="password" id="user-password">
				            <label class="mdl-textfield__label" for="user-password">
				                <spring:message code="login.login.label.password"/>:
				            </label>
				        </div>
				
				        </div>
				
				        <!-- Form buttons -->
				        <div class="mdl-card__actions mdl-card--border">
				       	 	<div class="left">
				                <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				                    <spring:message code="login.login.button.submit"/>
				                </button>
				            </div>
				            <div class="right">
							    <button type="button" ng-click="create()" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
							    		<spring:message code="login.login.button.create.account"/>
							    </button>
							    <button type="button" ng-click="forgot()" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
							    		<spring:message code="login.login.button.forgot.password"/>
							    </button>
						    </div>
				        </div>
				    </div>
				</form>
				
				<!-- Social Sign In Buttons -->
				<h2 class="mdl-subtitle-text max-spacer-up">
					<spring:message code="login.login.signin.title"/>
				</h2>
				
				<div class="mdl-grid">
				    <!-- Add Facebook sign in button -->
					<div class="mdl-cell mdl-cell--6-col">
				        <button ng-click="loginFacebook()" class="wide mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect btn-social btn-facebook">
				            <span class="fa fa-facebook"></span>
				            <spring:message code="login.login.signin.facebook"/>
				        </button>
					</div>
				
				    <!-- Add Twitter sign in Button -->
					<div class="mdl-cell mdl-cell--6-col">
				        <button ng-click="loginTwitter()" class="wide mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect btn-social btn-twitter">
				            <span class="fa fa-twitter"></span>
				            <spring:message code="login.login.signin.twitter"/>
				        </button>
					</div>
				</div>
            </sec:authorize>

			<sec:authorize access="isAuthenticated()">
			    <p><spring:message code="login.login.message.already.authenticated"/></p>
			</sec:authorize>

        </div>

        <div class="mdl-cell mdl-cell--3-col">
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/login/login.js"></script>
