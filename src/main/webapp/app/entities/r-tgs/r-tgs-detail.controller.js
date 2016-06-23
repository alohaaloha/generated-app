(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RTGSDetailController', RTGSDetailController);

    RTGSDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'RTGS', 'AnalitikaIzvoda'];

    function RTGSDetailController($scope, $rootScope, $stateParams, entity, RTGS, AnalitikaIzvoda) {
        var vm = this;
        vm.rTGS = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:rTGSUpdate', function(event, result) {
            vm.rTGS = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
