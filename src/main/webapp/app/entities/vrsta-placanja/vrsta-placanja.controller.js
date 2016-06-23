(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('VrstaPlacanjaController', VrstaPlacanjaController);

    VrstaPlacanjaController.$inject = ['$scope', '$state', 'VrstaPlacanja'];

    function VrstaPlacanjaController ($scope, $state, VrstaPlacanja) {
        var vm = this;
        vm.vrstaPlacanjas = [];
        vm.loadAll = function() {
            VrstaPlacanja.query(function(result) {
                vm.vrstaPlacanjas = result;
            });
        };

        vm.loadAll();
        
    }
})();
