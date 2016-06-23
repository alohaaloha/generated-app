(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('PravnoLiceDeleteController',PravnoLiceDeleteController);

    PravnoLiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'PravnoLice'];

    function PravnoLiceDeleteController($uibModalInstance, entity, PravnoLice) {
        var vm = this;
        vm.pravnoLice = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            PravnoLice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
