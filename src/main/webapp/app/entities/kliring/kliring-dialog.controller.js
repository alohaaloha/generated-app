(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KliringDialogController', KliringDialogController);

    KliringDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Kliring', 'StavkaKliringa', 'Valuta'];

    function KliringDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Kliring, StavkaKliringa, Valuta) {
        var vm = this;

        vm.kliring = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.stavkakliringas = StavkaKliringa.query();
        vm.valutas = Valuta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.kliring.id !== null) {
                Kliring.update(vm.kliring, onSaveSuccess, onSaveError);
            } else {
                Kliring.save(vm.kliring, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pinfProApp:kliringUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datumValute = false;
        vm.datePickerOpenStatus.datum = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
