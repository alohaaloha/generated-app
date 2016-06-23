(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('PravnoLiceDetailController', PravnoLiceDetailController);

    PravnoLiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PravnoLice'];

    function PravnoLiceDetailController($scope, $rootScope, $stateParams, entity, PravnoLice) {
        var vm = this;
        vm.pravnoLice = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:pravnoLiceUpdate', function(event, result) {
            vm.pravnoLice = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
