(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('UkidanjeController', UkidanjeController);

    UkidanjeController.$inject = ['$scope', '$state', 'Ukidanje'];

    function UkidanjeController ($scope, $state, Ukidanje) {
        var vm = this;
        vm.ukidanjes = [];
        vm.loadAll = function() {
            Ukidanje.query(function(result) {
                vm.ukidanjes = result;
            });
        };

        vm.loadAll();
        
    }
})();
