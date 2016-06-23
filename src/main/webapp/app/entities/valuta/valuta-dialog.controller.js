(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ValutaDialogController', ValutaDialogController);

    ValutaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Valuta', 'Drzava', 'Kliring', 'KursUValuti', 'RacunPravnogLica', 'AnalitikaIzvoda'];

    function ValutaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Valuta, Drzava, Kliring, KursUValuti, RacunPravnogLica, AnalitikaIzvoda) {
        var vm = this;
        vm.valuta = entity;
        vm.drzavas = Drzava.query();
        vm.klirings = Kliring.query();
        vm.kursuvalutis = KursUValuti.query();
        vm.racunpravnoglicas = RacunPravnogLica.query();
        vm.analitikaizvodas = AnalitikaIzvoda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:valutaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.valuta.id !== null) {
                Valuta.update(vm.valuta, onSaveSuccess, onSaveError);
            } else {
                Valuta.save(vm.valuta, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
