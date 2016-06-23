(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('AnalitikaIzvodaDeleteController',AnalitikaIzvodaDeleteController);

    AnalitikaIzvodaDeleteController.$inject = ['$uibModalInstance', 'entity', 'AnalitikaIzvoda'];

    function AnalitikaIzvodaDeleteController($uibModalInstance, entity, AnalitikaIzvoda) {
        var vm = this;
        vm.analitikaIzvoda = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            AnalitikaIzvoda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
