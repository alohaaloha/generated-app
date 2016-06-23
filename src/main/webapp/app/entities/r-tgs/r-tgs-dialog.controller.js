(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RTGSDialogController', RTGSDialogController);

    RTGSDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RTGS', 'AnalitikaIzvoda'];

    function RTGSDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RTGS, AnalitikaIzvoda) {
        var vm = this;
        vm.rTGS = entity;
        vm.analitikaizvodas = AnalitikaIzvoda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:rTGSUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.rTGS.id !== null) {
                RTGS.update(vm.rTGS, onSaveSuccess, onSaveError);
            } else {
                RTGS.save(vm.rTGS, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
