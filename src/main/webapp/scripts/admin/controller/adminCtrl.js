(function(){
	angular
	  .module('nodeCodeApp.Admin')
	  .controller('AdminCtrl',AdminCtrl);
	
	AdminCtrl.$inject = ['$log','ngTableParams','$scope','$filter','AdminService','$mdDialog'];
	function AdminCtrl($log,NgTableParams,$scope,$filter,AdminService, $mdDialog){
		var self = this;
		self.states = [{name:'Active'},{name:'Inactive'}];
		self.checkboxes = { checkBoxes: true, };
		self.unique  = false;
		/*self.tableParams = new NgTableParams(
				{
				}, 
				{	
	        getData: function(params) {
	          if(params.filter().name || params.filter().description){	
	        	return $filter('filter')(self.tableParams.settings().dataset, params.filter());
	          }
	          if(!self.tableParams.shouldGetData){
	        	  return self.tableParams.settings().dataset;
	          }
	          $log.info("Data getting from admin ajax ..."); 
	          return  AdminService.getUserRoles().then(function(data){
                  self.originalData = angular.copy(data);
	        	  self.tableParams.settings().dataset = data;
	        	  self.tableParams.shouldGetData = true;
                  return data;
              });
	         }
	      });*/
		self.tableParams = new NgTableParams(
				{
				}, 
				{	
	        getData: function(params) {
	        	
	        	if(!$scope.data){
	        		self.tableParams.shouldGetData = true;
	        	}
	        	else{
	        		var orderedFilteredDataset = $scope.data;
	        		if (params.sorting().userName
		           		  || params.sorting().contactName
		          		  || params.sorting().email
		          		  || params.sorting().state
	        			  || params.sorting().rolesStr){
	        			
		        		self.tableParams.shouldGetData = false;
		        		orderedFilteredDataset = $filter('orderBy')($scope.data, params.orderBy());			  				
			  		}
	        		
			        if(params.filter().userName 
	        		  || params.filter().contactName 
	        		  || params.filter().email
	        		  || params.filter().state 
	        		  || params.filter().rolesStr){	
			        	
			        	self.tableParams.shouldGetData = false;
			        	orderedFilteredDataset = $filter('filter')(orderedFilteredDataset, params.filter());			        	
			        }
			        
			        self.tableParams.settings().dataset = orderedFilteredDataset;	        		
	        	}

	          if(!self.tableParams.shouldGetData){
	        	  return self.tableParams.settings().dataset;
	          }
	          $log.info("Data getting from admin ajax ..."); 
	          return  AdminService.getUserRoles().then(function(data){
	        	  _.each(data,function(user){
	        		  user.rolesStr = _.map(user.roles, 'name').join(', ');
	        		  user.contactName = user.firstName +' '+ user.lastName;
	        	  })
	        	  $scope.data = data;
	        	  self.tableParams.settings().dataset = data;
	        	  self.tableParams.shouldGetData = true;
                  return data;
              });
	         }
	      });
		self.tableParams.shouldGetData = true;
		
		self.showAddUserDialog = function(){
			self.newUser = {roles:[]};
			$mdDialog.show({
				scope: $scope,  
				preserveScope: true,
				templateUrl : 'scripts/admin/partials/addNewUser.html',
				clickOutsideToClose:false
			});
		}
		
		self.cancel = function(){
			$mdDialog.cancel();
		}
		AdminService.getRoles().then(function(data){
			self.roleList = data;
		});
		/**/
		self.addUserBtn = function(newUser){
			var roles = [];
			_.each(newUser.roles, function(role){
				roles.push(JSON.parse(role));
			})
			newUser.roles = roles;
			AdminService.addUser(newUser).then(function(response){
				if(response.status == 200){
						console.log("User added");
						self.tableParams.reload();
						$mdDialog.cancel();
				
					
				}else{
					$mdDialog.cancel();
				}
			});
		};
		
		self.showChangePasswordDialog = function(existingUser){
			$scope.existingUser = existingUser;
			$scope.existingUser.previousPassword = existingUser.password;
			$mdDialog.show({
				scope: $scope,  
				preserveScope: true,
				templateUrl : 'scripts/admin/partials/changePasswordDialog.html',
				clickOutsideToClose:false
			});
		}
		self.changePasswordBtn = function(existingUser){
			/*This will not work because existing password will be in encrypted form.*/
			if($scope.existingUser.previousPassword == existingUser.newPassword){
				alert("No changes on the password.");
				return;
			}
			$scope.existingUser.password = existingUser.newPassword;
			AdminService.updatePassword(existingUser).then(function(response){
				if(response.status == 200){
					console.log('User was successfully updated');
					self.tableParams.reload();
				}
				$mdDialog.cancel();
			});
		}
		
		self.showEditUserDialog = function(existingUser){
			$scope.existingUser = existingUser;
			$scope.existingUser.id = existingUser.id
			$scope.existingUser.roles = existingUser.userProfiles
			$mdDialog.show({
				scope: $scope,  
				preserveScope: true,
				templateUrl : 'scripts/admin/partials/editUser.html',
				clickOutsideToClose:false
			});
		}
		
		self.editUserBtn = function(existingUser){
			if(!existingUser.state){
				alert("State is a required field.");
				return;
			}
			if(!existingUser.userProfile){
				alert("Please select a role for the user.");
				return;
			}
			if(!existingUser.email){
				alert("Please enter valid email <example@example.com>");
				return;
			}
			AdminService.updateUser(existingUser).then(function(response){
				if(response.status == 200){
					var userId = response.data.id;
					self.tableParams.reload();
					$mdDialog.cancel();
				}else{
					$mdDialog.cancel();
				}
			});
		};
		
		
		
		
		
	}
	
})();