(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DnevnoStanjeRacunaDetailController', DnevnoStanjeRacunaDetailController);

    DnevnoStanjeRacunaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'DnevnoStanjeRacuna', 'AnalitikaIzvoda', 'RacunPravnogLica'];

    function DnevnoStanjeRacunaDetailController($scope, $rootScope, $stateParams, entity, DnevnoStanjeRacuna, AnalitikaIzvoda, RacunPravnogLica) {
        var vm = this;
        vm.dnevnoStanjeRacuna = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:dnevnoStanjeRacunaUpdate', function(event, result) {
            vm.dnevnoStanjeRacuna = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
