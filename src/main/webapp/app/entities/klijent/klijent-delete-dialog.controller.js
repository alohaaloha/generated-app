(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KlijentDeleteController',KlijentDeleteController);

    KlijentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Klijent'];

    function KlijentDeleteController($uibModalInstance, entity, Klijent) {
        var vm = this;
        vm.klijent = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Klijent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
