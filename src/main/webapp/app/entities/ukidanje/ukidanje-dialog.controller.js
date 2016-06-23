(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('UkidanjeDialogController', UkidanjeDialogController);

    UkidanjeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ukidanje', 'RacunPravnogLica'];

    function UkidanjeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ukidanje, RacunPravnogLica) {
        var vm = this;
        vm.ukidanje = entity;
        vm.racunpravnoglicas = RacunPravnogLica.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:ukidanjeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.ukidanje.id !== null) {
                Ukidanje.update(vm.ukidanje, onSaveSuccess, onSaveError);
            } else {
                Ukidanje.save(vm.ukidanje, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datumUkidanja = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
