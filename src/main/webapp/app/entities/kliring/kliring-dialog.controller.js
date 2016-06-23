(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KliringDialogController', KliringDialogController);

    KliringDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Kliring', 'StavkaKliringa', 'Valuta'];

    function KliringDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Kliring, StavkaKliringa, Valuta) {
        var vm = this;
        vm.kliring = entity;
        vm.stavkakliringas = StavkaKliringa.query();
        vm.valutas = Valuta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:kliringUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kliring.id !== null) {
                Kliring.update(vm.kliring, onSaveSuccess, onSaveError);
            } else {
                Kliring.save(vm.kliring, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datumValute = false;
        vm.datePickerOpenStatus.datum = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
