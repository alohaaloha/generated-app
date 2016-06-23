(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaController', RacunPravnogLicaController);

    RacunPravnogLicaController.$inject = ['$scope', '$state', 'RacunPravnogLica'];

    function RacunPravnogLicaController ($scope, $state, RacunPravnogLica) {
        var vm = this;
        vm.racunPravnogLicas = [];
        vm.loadAll = function() {
            RacunPravnogLica.query(function(result) {
                vm.racunPravnogLicas = result;
            });
        };

        vm.loadAll();
        
    }
})();
