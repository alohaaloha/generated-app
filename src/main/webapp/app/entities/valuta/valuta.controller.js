(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ValutaController', ValutaController);

    ValutaController.$inject = ['$scope', '$state', 'Valuta'];

    function ValutaController ($scope, $state, Valuta) {
        var vm = this;
        vm.valutas = [];
        vm.loadAll = function() {
            Valuta.query(function(result) {
                vm.valutas = result;
            });
        };

        vm.loadAll();
        
    }
})();
