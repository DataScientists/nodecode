(function(){
	
angular.module('nodeCodeApp')   
.directive('userExist', ['$http','$q', function($http,$q) {
  return {
    require : 'ngModel',
   
    link : function($scope, element, attrs, ngModel) {
      ngModel.$asyncValidators.userExist = function(userName) {
        return $http.get('web/rest/admin/findByUserName?userName='+ userName).then(
                function(response) {
                    if (response.data.length !== 0) {
                         return $q.reject(); 
                        
                    }
               return true;
           }
       );;
      };
    }
  }
}]).directive('passwordCharactersValidator', function() {

    var REQUIRED_PATTERNS = [
      /\d+/,    //numeric values
      /[a-z]+/, //lowercase values
      /[A-Z]+/, //uppercase values
      /\W+/,    //special characters
      /^\S+$/   //no whitespace allowed
    ];

    return {
      require : 'ngModel',
      link : function($scope, element, attrs, ngModel) {
        ngModel.$validators.passwordCharacters = function(value) {
          var status = true;
          angular.forEach(REQUIRED_PATTERNS, function(pattern) {
            status = status && pattern.test(value);
          });
          return status;
        }; 
      }
    }
  }).directive('emailExist', ['$http','$q', function($http,$q) {
	  return {
		    require : 'ngModel',
		   
		    link : function($scope, element, attrs, ngModel) {
		      ngModel.$asyncValidators.emailExist = function(email) {
		        return $http.get('web/rest/admin/findByUserEmail?email='+ email).then(
		                function(response) {
		                    if (response.data.length !== 0) {
		                         return $q.reject(); 
		                        
		                    }
		               return true;
		           }
		       );;
		      };
		    }
		  }
		}])

})();
