(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoDialogController', NaseljenoMestoDialogController);

    NaseljenoMestoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NaseljenoMesto', 'Drzava'];

    function NaseljenoMestoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NaseljenoMesto, Drzava) {
        var vm = this;
        vm.naseljenoMesto = entity;
        vm.drzavas = Drzava.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:naseljenoMestoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.naseljenoMesto.id !== null) {
                NaseljenoMesto.update(vm.naseljenoMesto, onSaveSuccess, onSaveError);
            } else {
                NaseljenoMesto.save(vm.naseljenoMesto, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
