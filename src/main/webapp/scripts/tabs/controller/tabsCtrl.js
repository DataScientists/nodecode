(function() {
    angular.module("nodeCodeApp.Tabs").controller("TabsCtrl", TabsCtrl);

    TabsCtrl.$inject = ['$scope', '$state', '$rootScope', '$log', '$stickyState', 'AuthenticationService'];

    function TabsCtrl($scope, $state, $rootScope, $log, $stickyState, auth) {
        $scope.loading = false;
        $scope.tabOptions = [];
        if (auth.isLoggedIn() && auth.userHasPermission(['ADMIN'])) {
        	$scope.tabOptions.push({
        		state: "tabs.admin",
        		data: ""
        	});
        }
        
        if (auth.isLoggedIn() && auth.userHasPermission(['USER','ADMIN'])) {
            $scope.tabOptions.push({
                state: "tabs.nodes",
                data: ""
            });
        }
        
        if (auth.isLoggedIn() && auth.userHasPermission(['ADMIN'])) {
            $scope.tabOptions.push({
                state: "tabs.jmx",
                data: ""
            });
        }
        
        $scope.$watch('selectedIndex', function(current, old) {
            var state = null;
            var data = null;
            if ($scope.tabOptions[current]) {
                if ($scope.tabOptions[current].state) {
                    $log.info("Navigating to " + $scope.tabOptions[current].state);
                    state = $scope.tabOptions[current].state;
                } else {
                	var msg = "No state in $scope.$watch - selectedIndex";
                	alert(msg);
                	console.error(msg);
                }
                if ($scope.tabOptions[current].data) {
                    data = $scope.tabOptions[current].data;
                }

            } else {
            	var msg = "No tabOptions in $scope.$watch - selectedIndex";
            	alert(msg);
            	console.error(msg);
            }
            $log.info("going to state " + state);
            $state.go(state, data);

        });

        var tabs = [];
        tabs.selected = null;
        tabs.previous = null;
        if (auth.isLoggedIn() && auth.userHasPermission(['ADMIN'])) {
        	tabs.push({
                title: 'Admin',
                viewName: 'admin@tabs'
            });
        }
        
        if (auth.isLoggedIn() && auth.userHasPermission(['USER', 'ADMIN'])) {
        	tabs.push({
                title: 'Nodes',
                viewName: 'nodes@tabs'
            });
        }
        
        if (auth.isLoggedIn() && auth.userHasPermission(['ADMIN'])) {
        	tabs.push({
                title: 'JMeter',
                viewName: 'jmx@tabs'
            });
        }
        
        $scope.tabs = tabs;
        $scope.selectedIndex = 0;


        $scope.addNewTab = function(row) {
            tabs.push({
                title: row.name,
                viewName: 'new@tabs',
                canClose: true,
                disabled: false
            });
            $scope.tabOptions.push({
                state: "tabs.new",
                data: {
                    row: row.idNode
                }
            });
            $rootScope.tabsLoading = true;
            safeDigest($rootScope.tabsLoading);
        };
        
        $rootScope.addErrorTab = function(error) {
        	var tabTitle = "Error!";
            var state = "tabs.error";
            $stickyState.reset(state);         
                tabs.push({
                    title: tabTitle,
                    viewName: 'error@tabs',
                    canClose: true,
                    disabled: false
                });
                $scope.tabOptions.push({
                    state: state,
                    data: {
                        error: error
                    }
                });
            
            $rootScope.tabsLoading = false;
            safeDigest($rootScope.tabsLoading);
        };

        $scope.removeTab = function(tab) {           	
            var index = tabs.indexOf(tab);
            tabs.splice(index, 1);
            $scope.tabOptions.splice(index, 1);  
        };
        
        $scope.turnOffProgressBar = function turnOffProgressBar() {
            $scope.loading = false;
            return 'Done';
        }

        $scope.tabMenu = function(tab) {
            if (tabs.indexOf(tab) > 1) {
                return [
                    ['Close Tab', function(tab) {
                        $scope.removeTab(tab);
                    }]
                ]
            } else {
                return [];
            }
        }
        function checkIfTabIsOpen(tabs, title) {
            var openedTab = false;
            _.find(tabs, function(el, ind) {
                if (el.title === title) {
                    $scope.selectedIndex = ind;
                    safeDigest($scope.selectedIndex);
                    openedTab = true;
                }
            });
            return openedTab;
        }

        var safeDigest = function(obj) {
            if (!obj.$$phase) {
                try {
                    obj.$digest();
                } catch (e) {}
            }
        }
    }
})();
