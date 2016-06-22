(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaDeleteController',DrzavaDeleteController);

    DrzavaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Drzava'];

    function DrzavaDeleteController($uibModalInstance, entity, Drzava) {
        var vm = this;
        vm.drzava = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Drzava.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
