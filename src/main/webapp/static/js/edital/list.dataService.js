App.factory("dataService", ["$http", function ($http) {
	return {
		lista: function(params) {
			return $http.get(contextPath + "/edital?page=" + params.page + "&size=" + params.size + "&nome=" + (params.nome || ""));
		},
		
		remove: function(id) {
			return $http.delete(contextPath + "/edital/" + id + "?" + csrf.name + "=" + csrf.value);
		}
	};
}]);
