(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RTGSController', RTGSController);

    RTGSController.$inject = ['$scope', '$state', 'RTGS'];

    function RTGSController ($scope, $state, RTGS) {
        var vm = this;
        vm.rTGS = [];
        vm.loadAll = function() {
            RTGS.query(function(result) {
                vm.rTGS = result;
            });
        };

        vm.loadAll();
        
    }
})();
