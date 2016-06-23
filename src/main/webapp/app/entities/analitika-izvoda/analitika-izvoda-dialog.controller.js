(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('AnalitikaIzvodaDialogController', AnalitikaIzvodaDialogController);

    AnalitikaIzvodaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AnalitikaIzvoda', 'DnevnoStanjeRacuna', 'NaseljenoMesto', 'VrstaPlacanja', 'Valuta', 'RTGS', 'StavkaKliringa'];

    function AnalitikaIzvodaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AnalitikaIzvoda, DnevnoStanjeRacuna, NaseljenoMesto, VrstaPlacanja, Valuta, RTGS, StavkaKliringa) {
        var vm = this;
        vm.analitikaIzvoda = entity;
        vm.dnevnostanjeracunas = DnevnoStanjeRacuna.query();
        vm.naseljenomestos = NaseljenoMesto.query();
        vm.vrstaplacanjas = VrstaPlacanja.query();
        vm.valutas = Valuta.query();
        vm.rtgs = RTGS.query();
        vm.stavkakliringas = StavkaKliringa.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:analitikaIzvodaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.analitikaIzvoda.id !== null) {
                AnalitikaIzvoda.update(vm.analitikaIzvoda, onSaveSuccess, onSaveError);
            } else {
                AnalitikaIzvoda.save(vm.analitikaIzvoda, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datumPrijema = false;
        vm.datePickerOpenStatus.datumValute = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
