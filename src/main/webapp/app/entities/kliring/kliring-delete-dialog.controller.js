(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KliringDeleteController',KliringDeleteController);

    KliringDeleteController.$inject = ['$uibModalInstance', 'entity', 'Kliring'];

    function KliringDeleteController($uibModalInstance, entity, Kliring) {
        var vm = this;
        vm.kliring = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Kliring.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
