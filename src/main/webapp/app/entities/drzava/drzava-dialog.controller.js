(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaDialogController', DrzavaDialogController);

    DrzavaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Drzava', 'NaseljenoMesto', 'Valuta'];

    function DrzavaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Drzava, NaseljenoMesto, Valuta) {
        var vm = this;
        vm.drzava = entity;
        vm.naseljenomestos = NaseljenoMesto.query();
        vm.valutas = Valuta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:drzavaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.drzava.id !== null) {
                Drzava.update(vm.drzava, onSaveSuccess, onSaveError);
            } else {
                Drzava.save(vm.drzava, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
