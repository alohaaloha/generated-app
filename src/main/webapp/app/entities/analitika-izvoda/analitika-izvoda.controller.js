(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('AnalitikaIzvodaController', AnalitikaIzvodaController);

    AnalitikaIzvodaController.$inject = ['$scope', '$state', 'AnalitikaIzvoda'];

    function AnalitikaIzvodaController ($scope, $state, AnalitikaIzvoda) {
        var vm = this;
        vm.analitikaIzvodas = [];
        vm.loadAll = function() {
            AnalitikaIzvoda.query(function(result) {
                vm.analitikaIzvodas = result;
            });
        };

        vm.loadAll();
        
    }
})();
