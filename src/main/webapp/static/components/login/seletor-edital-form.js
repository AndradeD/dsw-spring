/*
 * Traducao
 */
App.config(function($translateProvider) {
	$translateProvider.translations('pt', {
		form_title: 'Faça login usando a sua conta',
		login_failed_error: 'Credenciais inválidas',
		label_email: 'E-mail',
		label_password: 'Senha',
		button_submit: 'Envia',
		button_create_account: 'Criar nova conta',
		button_forgot_password: 'Esqueci minha senha'
	})
	
	.translations('en', {
		form_title: 'Login using your account',
		login_failed_error: 'Invalid credentials',
		label_email: 'E-mail',
		label_password: 'Password',
		button_submit: 'Send',
		button_create_account: 'Create new account',
		button_forgot_password: 'Forgot my password'
	});
	
	$translateProvider.preferredLanguage('pt');
});


/*
 * Controller
 */
var seletorEditalFormController = function ($scope, $window, $translate) {
	$scope.editais = [];
	$scope.csrf = csrf;

//	$scope.forgot = function() {
//		$window.location.href = contextPath + "/login/forgot";
//	}
//	  
//	$scope.create = function() {
//		$window.location.href = contextPath + "/login/create";
//	}
//	  
//	$scope.loginTwitter = function() {
//		$window.location.href = contextPath + "/auth/twitter";
//	}
//	  
//	$scope.loginFacebook = function() {
//		$window.location.href = contextPath + "/auth/facebook";
//	}
	
	carrega: function() {
		return $http.get(contextPath + "/edital/summary");
	}

	carrega().then(function(data) {
		$log.info(data);
		$scope.editais = data;
	});

    var dialog = document.querySelector('dialog');
//    var showDialogButton = document.querySelector('#show-dialog');
    
    if (!dialog.showModal) {
    		dialogPolyfill.registerDialog(dialog);
    }
    
//    showDialogButton.addEventListener('click', function() {
//      dialog.showModal();
//    });
    
//    dialog.querySelector('.close').addEventListener('click', function() {
//      dialog.close();
//    });	 
}


/*
 * Componente
 */
App.component('seletorEditalForm', {
	templateUrl: '/selecaoppgi/static/components/login/seletor-edital-form.html',
	controller:  seletorEditalFormController
});