(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaDeleteController',RacunPravnogLicaDeleteController);

    RacunPravnogLicaDeleteController.$inject = ['$uibModalInstance', 'entity', 'RacunPravnogLica'];

    function RacunPravnogLicaDeleteController($uibModalInstance, entity, RacunPravnogLica) {
        var vm = this;
        vm.racunPravnogLica = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            RacunPravnogLica.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
