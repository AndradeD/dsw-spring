App.controller("inscricaoController", function ($scope, inscricaoDataService, NgTableParams) {
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
		atualizaLista();
	}
	
	/*
	 * Atualiza a lista de inscrições
	 */
	var atualizaLista = function() {
		$scope.tableParams.reload();
	}
      
	self.homologarOriginal = function(id,homologado,justificativa) {
		homologarOriginal(id,homologado,justificativa);
	}
    
    var homologarOriginal = function(id,homologado,justificativa) {
    	return inscricaoDataService.homologarOriginal({
    		id:id,
    		homologado:homologado,
    		justificativa:justificativa
    	});
    }
    
    self.homologarRecurso = function(id,homologado,justificativa) {
		homologarRecurso(id,homologado,justificativa);
	}
	
    var homologarRecurso = function(id,homologado,justificativa) {
    	return inscricaoDataService.homologarRecurso({
    		id:id,
    		homologado:homologado,
    		justificativa:justificativa
    	});
    }
	/*
	 * Prepara a tabela
	 */
	$scope.tableParams = new NgTableParams({}, {
		getData: function (params) {
			return inscricaoDataService.lista({
				id: idEdital,
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