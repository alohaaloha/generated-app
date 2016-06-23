(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('PravnoLiceDialogController', PravnoLiceDialogController);

    PravnoLiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PravnoLice'];

    function PravnoLiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PravnoLice) {
        var vm = this;
        vm.pravnoLice = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:pravnoLiceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.pravnoLice.id !== null) {
                PravnoLice.update(vm.pravnoLice, onSaveSuccess, onSaveError);
            } else {
                PravnoLice.save(vm.pravnoLice, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
