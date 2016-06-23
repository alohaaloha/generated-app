(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DnevnoStanjeRacunaDeleteController',DnevnoStanjeRacunaDeleteController);

    DnevnoStanjeRacunaDeleteController.$inject = ['$uibModalInstance', 'entity', 'DnevnoStanjeRacuna'];

    function DnevnoStanjeRacunaDeleteController($uibModalInstance, entity, DnevnoStanjeRacuna) {
        var vm = this;
        vm.dnevnoStanjeRacuna = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            DnevnoStanjeRacuna.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
