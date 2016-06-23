(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('UkidanjeDeleteController',UkidanjeDeleteController);

    UkidanjeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ukidanje'];

    function UkidanjeDeleteController($uibModalInstance, entity, Ukidanje) {
        var vm = this;
        vm.ukidanje = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Ukidanje.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
