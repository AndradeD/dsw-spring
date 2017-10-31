App.controller("listaController", function ($scope, dataService, NgTableParams) {
	var self = this;
	
	/**
	 * Filtros
	 */
	$scope.filtros = {
		nome: ""
	}
	
	/*
	 * Altera os filtros de consulta
	 */
	self.atualizaFiltro = function () {
		alert('aa');
		atualizaLista();
	}
	
	/*
	 * Atualiza a lista de editais
	 */
	var atualizaLista = function() {
		$scope.tableParams.reload();
	}
	
	/*
	 * Navega para a pagina de visualizacao de edital
	 */
	self.edita = function(id) {
		window.location = contextPath + "/edital/edit/" + id;
	}
	
	/*
	 * Cria um novo edital
	 */
	self.novo = function() {
		window.location = contextPath + "/edital/create";
	}
	
	/*
	 * Remove o edital selecionado
	 */
	self.remove = function(id) {
		dataService.remove(id).then(atualizaLista);
	}
	
	/*
	 * Prepara a tabela
	 */
	$scope.tableParams = new NgTableParams({}, {
		getData: function (params) {
			return dataService.lista({
				page: params.page() - 1,
				size: params.count(),
				nome: $scope.filtros.nome
			}).then(function (data) {
				if(data.data.TotalRecordCount == 0) {
					self.noSite = true;
				}
				else {
					params.total(data.data.TotalRecordCount);
					self.noSite = false;
					return data = data.data.Records;
				}
			});
		}
	});
});