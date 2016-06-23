(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('BankaController', BankaController);

    BankaController.$inject = ['$scope', '$state', 'Banka'];

    function BankaController ($scope, $state, Banka) {
        var vm = this;
        vm.bankas = [];
        vm.loadAll = function() {
            Banka.query(function(result) {
                vm.bankas = result;
            });
        };

        vm.loadAll();
        
    }
})();
