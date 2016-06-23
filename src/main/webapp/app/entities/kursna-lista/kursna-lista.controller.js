(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursnaListaController', KursnaListaController);

    KursnaListaController.$inject = ['$scope', '$state', 'KursnaLista'];

    function KursnaListaController ($scope, $state, KursnaLista) {
        var vm = this;
        vm.kursnaListas = [];
        vm.loadAll = function() {
            KursnaLista.query(function(result) {
                vm.kursnaListas = result;
            });
        };

        vm.loadAll();
        
    }
})();
