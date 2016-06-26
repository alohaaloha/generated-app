(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KliringController', KliringController);

    KliringController.$inject = ['$scope', '$state', 'Kliring'];

    function KliringController ($scope, $state, Kliring) {
        var vm = this;
        
        vm.klirings = [];

        loadAll();

        function loadAll() {
            Kliring.query(function(result) {
                vm.klirings = result;
            });
        }
    }
})();
