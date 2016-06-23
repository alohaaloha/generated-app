(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KlijentController', KlijentController);

    KlijentController.$inject = ['$scope', '$state', 'Klijent'];

    function KlijentController ($scope, $state, Klijent) {
        var vm = this;
        vm.klijents = [];
        vm.loadAll = function() {
            Klijent.query(function(result) {
                vm.klijents = result;
            });
        };

        vm.loadAll();
        
    }
})();
