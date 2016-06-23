(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('StavkaKliringaDeleteController',StavkaKliringaDeleteController);

    StavkaKliringaDeleteController.$inject = ['$uibModalInstance', 'entity', 'StavkaKliringa'];

    function StavkaKliringaDeleteController($uibModalInstance, entity, StavkaKliringa) {
        var vm = this;
        vm.stavkaKliringa = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            StavkaKliringa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
