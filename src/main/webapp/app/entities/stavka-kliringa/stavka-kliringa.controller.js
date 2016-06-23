(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('StavkaKliringaController', StavkaKliringaController);

    StavkaKliringaController.$inject = ['$scope', '$state', 'StavkaKliringa'];

    function StavkaKliringaController ($scope, $state, StavkaKliringa) {
        var vm = this;
        vm.stavkaKliringas = [];
        vm.loadAll = function() {
            StavkaKliringa.query(function(result) {
                vm.stavkaKliringas = result;
            });
        };

        vm.loadAll();
        
    }
})();
