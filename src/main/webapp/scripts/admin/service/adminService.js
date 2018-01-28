(function() {
	angular.module('nodeCodeApp.Admin')
			.service('AdminService', AdminService);

	AdminService.$inject = [ '$http', '$q' ];
	function AdminService($http, $q) {
		var apiUrl = 'web/rest/';
		var adminEndpoint = apiUrl + 'admin';
		var roleEndPoint = apiUrl + 'role';
		var getUserRoles = function() {
			return $http.get(adminEndpoint + '/list', {
				cache : false
			}).then(function(response) {
				var data = response.data;
				return data;
			});
		};
		
		var getRoles = function() {
			return $http.get(roleEndPoint + '/list', {
				cache : false
			}).then(function(response) {
				var data = response.data;
				return data;
			});
		};
		
		var findByUserName = function(userName) {
			return $http.get(adminEndpoint + '/findByUserName?userName='+ userName, {
				cache : false
			}).then(function(response) {
				var data = response.data;
				return data;
			});
		};
		
		var addUser = function(data){
			var restSaveUrl = adminEndpoint +'/save';
			var request =  $http({
				  method: 'POST',
				  url: restSaveUrl,
				  data:data
				})
			return request.then(handleSuccess,handleError);
		};
		
		var updatePassword = function(data){
			var restSaveUrl = adminEndpoint +'/save';
			var request =  $http({
				  method: 'POST',
				  url: restSaveUrl,
				  data:data
				})
			return request.then(handleSuccess,handleError);
		}
		
		
		function handleError(response) {
			if (!angular.isObject(response.data) || !response.data.message) {
				return ($q.reject("An unknown error occurred."));
			}
			return ($q.reject(response.data.message));
		}

		function handleSuccess(response) {
			return (response);
		}

		return {
			getUserRoles : getUserRoles,
			getRoles : getRoles,
			addUser : addUser,
			findByUserName :findByUserName,
			updatePassword : updatePassword
		};
	}

})();