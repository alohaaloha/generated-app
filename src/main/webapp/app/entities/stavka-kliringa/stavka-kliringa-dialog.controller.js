(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('StavkaKliringaDialogController', StavkaKliringaDialogController);

    StavkaKliringaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StavkaKliringa', 'Kliring', 'AnalitikaIzvoda'];

    function StavkaKliringaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StavkaKliringa, Kliring, AnalitikaIzvoda) {
        var vm = this;
        vm.stavkaKliringa = entity;
        vm.klirings = Kliring.query();
        vm.analitikaizvodas = AnalitikaIzvoda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:stavkaKliringaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.stavkaKliringa.id !== null) {
                StavkaKliringa.update(vm.stavkaKliringa, onSaveSuccess, onSaveError);
            } else {
                StavkaKliringa.save(vm.stavkaKliringa, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
