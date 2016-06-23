(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RTGSDeleteController',RTGSDeleteController);

    RTGSDeleteController.$inject = ['$uibModalInstance', 'entity', 'RTGS'];

    function RTGSDeleteController($uibModalInstance, entity, RTGS) {
        var vm = this;
        vm.rTGS = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            RTGS.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
