App.factory("dispensaDataService", ["$http", function ($http) {
	return {
		lista: function(params) {
			return $http.get(contextPath + "/edital/homologacao/dispensa?idEdital=" + params.id +"&page=" + params.page + "&size=" + params.size + "&nome=" + (params.nome || ""));
		}
	};
}]);
