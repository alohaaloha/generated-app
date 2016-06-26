(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KliringDetailController', KliringDetailController);

    KliringDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Kliring', 'StavkaKliringa', 'Valuta'];

    function KliringDetailController($scope, $rootScope, $stateParams, entity, Kliring, StavkaKliringa, Valuta) {
        var vm = this;

        vm.kliring = entity;

        var unsubscribe = $rootScope.$on('pinfProApp:kliringUpdate', function(event, result) {
            vm.kliring = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
