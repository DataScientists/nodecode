(function(){
	angular
	  .module('nodeCodeApp.Node')
	  .controller('NodeCtrl',NodeCtrl);
	
	NodeCtrl.$inject = ['$log','ngTableParams','$scope','$filter'];
	function NodeCtrl($log,NgTableParams,$scope,$filter){
		var self = this;
		
		self.tableParams = new NgTableParams(
				{
				}, 
				{	
	        getData: function(params) {
	        	return [];
	        }
	      });
		self.tableParams.shouldGetData = true;
		
	}
	
})();