(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoDeleteController',NaseljenoMestoDeleteController);

    NaseljenoMestoDeleteController.$inject = ['$uibModalInstance', 'entity', 'NaseljenoMesto'];

    function NaseljenoMestoDeleteController($uibModalInstance, entity, NaseljenoMesto) {
        var vm = this;
        vm.naseljenoMesto = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            NaseljenoMesto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
