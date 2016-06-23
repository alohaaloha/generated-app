(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DnevnoStanjeRacunaDialogController', DnevnoStanjeRacunaDialogController);

    DnevnoStanjeRacunaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DnevnoStanjeRacuna', 'AnalitikaIzvoda', 'RacunPravnogLica'];

    function DnevnoStanjeRacunaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DnevnoStanjeRacuna, AnalitikaIzvoda, RacunPravnogLica) {
        var vm = this;
        vm.dnevnoStanjeRacuna = entity;
        vm.analitikaizvodas = AnalitikaIzvoda.query();
        vm.racunpravnoglicas = RacunPravnogLica.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:dnevnoStanjeRacunaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.dnevnoStanjeRacuna.id !== null) {
                DnevnoStanjeRacuna.update(vm.dnevnoStanjeRacuna, onSaveSuccess, onSaveError);
            } else {
                DnevnoStanjeRacuna.save(vm.dnevnoStanjeRacuna, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datum = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
