(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KliringController', KliringController);

    KliringController.$inject = ['$scope', '$state', 'Kliring'];

    function KliringController ($scope, $state, Kliring) {
        var vm = this;
        vm.klirings = [];
        vm.loadAll = function() {
            Kliring.query(function(result) {
                vm.klirings = result;
            });
        };

        vm.loadAll();
        
    }
})();
