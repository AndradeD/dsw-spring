App.factory("inscricaoDataService", ["$http", function ($http) {
	return {
		lista: function(params) {
			return $http.get(contextPath + "/edital/homologacao/inscricao?idEdital=" + params.id +"&page=" + params.page + "&size=" + params.size + "&nome=" + (params.nome || ""));
		},
		homologarOriginal:function(params){
			return $http.post(contextPath + "/edital/homologacao/inscricao/original",  {"id":params.id,"homologado":params.homologado,"justificativa":params.justificativa},{headers: { "X-CSRF-TOKEN": csrf.value }});						
		},
		homologarRecurso:function(params){
			return $http.post(contextPath + "/edital/homologacao/inscricao/recurso", {"id":params.id,"homologado":params.homologado,"justificativa":params.justificativa},{headers: {"X-CSRF-TOKEN": csrf.value }});
		}
	};
}]);
