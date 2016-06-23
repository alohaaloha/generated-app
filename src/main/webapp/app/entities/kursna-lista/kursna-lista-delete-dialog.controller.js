(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursnaListaDeleteController',KursnaListaDeleteController);

    KursnaListaDeleteController.$inject = ['$uibModalInstance', 'entity', 'KursnaLista'];

    function KursnaListaDeleteController($uibModalInstance, entity, KursnaLista) {
        var vm = this;
        vm.kursnaLista = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            KursnaLista.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
