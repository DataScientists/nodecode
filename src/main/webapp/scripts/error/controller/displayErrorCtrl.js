(function(){
	angular.module('famersBlockchainApp.DisplayError')
	.filter("htmlToPlaintext", function() {
        return function(input) {
            return input.replace(/<[^>]+>/gm, '');
          }
        })
	.controller('DisplayErrorCtrl',DisplayErrorCtrl);
	
	DisplayErrorCtrl.$inject = ['$scope','error'];
	function DisplayErrorCtrl($scope,error){
		
		$scope.data = error;
	}
})();

