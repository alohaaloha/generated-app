(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('BankaDetailController', BankaDetailController);

    BankaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Banka', 'KursnaLista', 'RacunPravnogLica'];

    function BankaDetailController($scope, $rootScope, $stateParams, entity, Banka, KursnaLista, RacunPravnogLica) {
        var vm = this;
        vm.banka = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:bankaUpdate', function(event, result) {
            vm.banka = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
