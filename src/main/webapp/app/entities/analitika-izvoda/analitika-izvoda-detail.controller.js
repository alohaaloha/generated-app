(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('AnalitikaIzvodaDetailController', AnalitikaIzvodaDetailController);

    AnalitikaIzvodaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'AnalitikaIzvoda', 'DnevnoStanjeRacuna', 'NaseljenoMesto', 'VrstaPlacanja', 'Valuta', 'RTGS', 'StavkaKliringa'];

    function AnalitikaIzvodaDetailController($scope, $rootScope, $stateParams, entity, AnalitikaIzvoda, DnevnoStanjeRacuna, NaseljenoMesto, VrstaPlacanja, Valuta, RTGS, StavkaKliringa) {
        var vm = this;
        vm.analitikaIzvoda = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:analitikaIzvodaUpdate', function(event, result) {
            vm.analitikaIzvoda = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
