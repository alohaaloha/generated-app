(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('BankaDeleteController',BankaDeleteController);

    BankaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Banka'];

    function BankaDeleteController($uibModalInstance, entity, Banka) {
        var vm = this;
        vm.banka = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Banka.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
