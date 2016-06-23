(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('StavkaKliringaDetailController', StavkaKliringaDetailController);

    StavkaKliringaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'StavkaKliringa', 'Kliring', 'AnalitikaIzvoda'];

    function StavkaKliringaDetailController($scope, $rootScope, $stateParams, entity, StavkaKliringa, Kliring, AnalitikaIzvoda) {
        var vm = this;
        vm.stavkaKliringa = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:stavkaKliringaUpdate', function(event, result) {
            vm.stavkaKliringa = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
