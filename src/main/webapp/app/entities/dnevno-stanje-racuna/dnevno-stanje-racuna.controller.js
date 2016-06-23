(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DnevnoStanjeRacunaController', DnevnoStanjeRacunaController);

    DnevnoStanjeRacunaController.$inject = ['$scope', '$state', 'DnevnoStanjeRacuna'];

    function DnevnoStanjeRacunaController ($scope, $state, DnevnoStanjeRacuna) {
        var vm = this;
        vm.dnevnoStanjeRacunas = [];
        vm.loadAll = function() {
            DnevnoStanjeRacuna.query(function(result) {
                vm.dnevnoStanjeRacunas = result;
            });
        };

        vm.loadAll();
        
    }
})();
