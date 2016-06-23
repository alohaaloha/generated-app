(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursUValutiDeleteController',KursUValutiDeleteController);

    KursUValutiDeleteController.$inject = ['$uibModalInstance', 'entity', 'KursUValuti'];

    function KursUValutiDeleteController($uibModalInstance, entity, KursUValuti) {
        var vm = this;
        vm.kursUValuti = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            KursUValuti.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
