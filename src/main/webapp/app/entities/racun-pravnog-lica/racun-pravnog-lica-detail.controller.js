(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaDetailController', RacunPravnogLicaDetailController);

    RacunPravnogLicaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'RacunPravnogLica', 'Banka', 'Klijent', 'DnevnoStanjeRacuna', 'Valuta', 'Ukidanje'];

    function RacunPravnogLicaDetailController($scope, $rootScope, $stateParams, entity, RacunPravnogLica, Banka, Klijent, DnevnoStanjeRacuna, Valuta, Ukidanje) {
        var vm = this;
        vm.racunPravnogLica = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:racunPravnogLicaUpdate', function(event, result) {
            vm.racunPravnogLica = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
