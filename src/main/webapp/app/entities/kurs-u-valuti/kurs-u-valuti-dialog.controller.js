(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursUValutiDialogController', KursUValutiDialogController);

    KursUValutiDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KursUValuti', 'Valuta', 'KursnaLista'];

    function KursUValutiDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KursUValuti, Valuta, KursnaLista) {
        var vm = this;
        vm.kursUValuti = entity;
        vm.valutas = Valuta.query();
        vm.kursnalistas = KursnaLista.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:kursUValutiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.kursUValuti.id !== null) {
                KursUValuti.update(vm.kursUValuti, onSaveSuccess, onSaveError);
            } else {
                KursUValuti.save(vm.kursUValuti, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
