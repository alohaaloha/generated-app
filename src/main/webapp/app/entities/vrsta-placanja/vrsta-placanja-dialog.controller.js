(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('VrstaPlacanjaDialogController', VrstaPlacanjaDialogController);

    VrstaPlacanjaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VrstaPlacanja', 'AnalitikaIzvoda'];

    function VrstaPlacanjaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VrstaPlacanja, AnalitikaIzvoda) {
        var vm = this;
        vm.vrstaPlacanja = entity;
        vm.analitikaizvodas = AnalitikaIzvoda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:vrstaPlacanjaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.vrstaPlacanja.id !== null) {
                VrstaPlacanja.update(vm.vrstaPlacanja, onSaveSuccess, onSaveError);
            } else {
                VrstaPlacanja.save(vm.vrstaPlacanja, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
