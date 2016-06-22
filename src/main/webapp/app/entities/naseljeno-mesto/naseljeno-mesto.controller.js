(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoController', NaseljenoMestoController);

    NaseljenoMestoController.$inject = ['$scope', '$state', 'NaseljenoMesto'];

    function NaseljenoMestoController ($scope, $state, NaseljenoMesto) {
        var vm = this;
        vm.naseljenoMestos = [];
        vm.loadAll = function() {
            NaseljenoMesto.query(function(result) {
                vm.naseljenoMestos = result;
            });
        };

        vm.loadAll();
        
    }
})();
