(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaController', RacunPravnogLicaController);

    RacunPravnogLicaController.$inject = ['$scope', '$state', 'RacunPravnogLica','$rootScope', '$window'];

    function RacunPravnogLicaController ($scope, $state, RacunPravnogLica,$rootScope,$window) {
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
