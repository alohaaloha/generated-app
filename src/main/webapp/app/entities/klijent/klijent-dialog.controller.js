(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KlijentDialogController', KlijentDialogController);

    KlijentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Klijent', 'RacunPravnogLica', 'PravnoLice', 'NaseljenoMesto'];

    function KlijentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Klijent, RacunPravnogLica, PravnoLice, NaseljenoMesto) {
        var vm = this;
        vm.klijent = entity;
        vm.racunpravnoglicas = RacunPravnogLica.query();
        vm.pravnolice = PravnoLice.query();
        vm.naseljenomestos = NaseljenoMesto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:klijentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.klijent.id !== null) {
                Klijent.update(vm.klijent, onSaveSuccess, onSaveError);
            } else {
                Klijent.save(vm.klijent, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
