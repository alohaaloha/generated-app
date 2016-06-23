(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ValutaDeleteController',ValutaDeleteController);

    ValutaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Valuta'];

    function ValutaDeleteController($uibModalInstance, entity, Valuta) {
        var vm = this;
        vm.valuta = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Valuta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
