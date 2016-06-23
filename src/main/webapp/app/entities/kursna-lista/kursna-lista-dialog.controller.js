(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursnaListaDialogController', KursnaListaDialogController);

    KursnaListaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KursnaLista', 'Banka', 'KursUValuti'];

    function KursnaListaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KursnaLista, Banka, KursUValuti) {
        var vm = this;
        vm.kursnaLista = entity;
        vm.bankas = Banka.query();
        vm.kursuvalutis = KursUValuti.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:kursnaListaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kursnaLista.id !== null) {
                KursnaLista.update(vm.kursnaLista, onSaveSuccess, onSaveError);
            } else {
                KursnaLista.save(vm.kursnaLista, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datum = false;
        vm.datePickerOpenStatus.datumPrimene = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
