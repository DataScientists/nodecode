(function() {
    angular.module('nodeCodeApp.Logout',['ui.router','ngStorage']).config(Config);

    Config.$inject = ['$stateProvider'];

    function Config($stateProvider){
           $stateProvider
        .state('logout', {
            controller: 'LogoutCtrl'
        });
    }
})();