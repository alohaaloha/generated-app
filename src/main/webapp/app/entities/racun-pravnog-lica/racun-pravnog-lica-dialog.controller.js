(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaDialogController', RacunPravnogLicaDialogController);

    RacunPravnogLicaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RacunPravnogLica', 'Banka', 'Klijent', 'DnevnoStanjeRacuna', 'Valuta', 'Ukidanje'];

    function RacunPravnogLicaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RacunPravnogLica, Banka, Klijent, DnevnoStanjeRacuna, Valuta, Ukidanje) {
        var vm = this;
        vm.racunPravnogLica = entity;
        vm.bankas = Banka.query();
        vm.klijents = Klijent.query();
        vm.dnevnostanjeracunas = DnevnoStanjeRacuna.query();
        vm.valutas = Valuta.query();
        vm.ukidanjes = Ukidanje.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:racunPravnogLicaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.racunPravnogLica.id !== null) {
                RacunPravnogLica.update(vm.racunPravnogLica, onSaveSuccess, onSaveError);
            } else {
                RacunPravnogLica.save(vm.racunPravnogLica, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datumOtvaranja = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
