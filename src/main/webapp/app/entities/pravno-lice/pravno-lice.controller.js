(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('PravnoLiceController', PravnoLiceController);

    PravnoLiceController.$inject = ['$scope', '$state', 'PravnoLice'];

    function PravnoLiceController ($scope, $state, PravnoLice) {
        var vm = this;
        vm.pravnoLice = [];
        vm.loadAll = function() {
            PravnoLice.query(function(result) {
                vm.pravnoLice = result;
            });
        };

        vm.loadAll();
        
    }
})();
