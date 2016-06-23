(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursUValutiController', KursUValutiController);

    KursUValutiController.$inject = ['$scope', '$state', 'KursUValuti'];

    function KursUValutiController ($scope, $state, KursUValuti) {
        var vm = this;
        vm.kursUValutis = [];
        vm.loadAll = function() {
            KursUValuti.query(function(result) {
                vm.kursUValutis = result;
            });
        };

        vm.loadAll();
        
    }
})();
