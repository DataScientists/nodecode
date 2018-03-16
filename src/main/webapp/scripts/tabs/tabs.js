(function() {
	angular.module("nodeCodeApp.Tabs", [ 'ui.router', 'ct.ui.router.extras.core',
	                                     'ct.ui.router.extras.dsr',
	                                     'ct.ui.router.extras.sticky' ])
	.config(Config)
	.factory('TabsCache',TabsCache)
	.run(function ($rootScope, $state, $window, $timeout) {
		$rootScope.$state = $state;
	})
	.run(['$state', function ($state) {}]);
	
	TabsCache.$inject = ['$cacheFactory'];
	function TabsCache($cacheFactory){
		return $cacheFactory('tabs-cache');
	}

	Config.$inject = ['$stateProvider','$windowProvider','$rootScopeProvider','$stickyStateProvider'];
	function Config($stateProvider,$windowProvider,$rootScopeProvider,$stickyStateProvider) {
		var $log =  angular.injector(['ng']).get('$log');
		var $window = $windowProvider.$get();
		$rootScopeProvider.$window = $window;
		$stateProvider.state({
			name: 'tabs',
			templateUrl : "scripts/tabs/view/tabs.html",
			controller: 'TabsCtrl as vm',
			deepStateRedirect: true,
		}).state( {
            name:'tabs.admin',
            url: '/admin',
            authenticate:true,
            views:{
                'admin@tabs':{
                    templateUrl : "scripts/admin/view/admin.html",
                    controller: 'AdminCtrl as vm'

                }
            }
        }).state( {
            name:'tabs.nodes',
            url: '/nodes',
            authenticate:true,
            views:{
                'nodes@tabs':{
                    templateUrl : "scripts/nodes/view/nodes.html",
                    controller: 'NodeCtrl as vm'

                }
            }
        }).state( {
            name:'tabs.jmx',
            url: '/jmx',
            authenticate:true,
            views:{
                'jmx@tabs':{
                    templateUrl : "scripts/jmeter/view/jmeter.html",
                    controller: 'JMeterCtrl as vm'
                }
            }
        }).state( {
            name:'tabs.error',
        	url: '/displayerror/',
        	params: {
        		error: null
            },
        	sticky: true,
		    deepStateRedirect: true,
		    authenticate:false,
            views:{
                'error@tabs':{
                    templateUrl: 'scripts/error/view/debug.html',
                    controller: 'DisplayErrorCtrl as vm',
                    params:{error: null},
                    resolve:{
                        error: function($stateParams) {
                            return $stateParams.error;
                        }                   
                    }
                }
            }
        });
	}
})();