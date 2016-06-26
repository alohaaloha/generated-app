(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('BankaDialogController', BankaDialogController);

    BankaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Banka', 'KursnaLista', 'RacunPravnogLica'];

    function BankaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Banka, KursnaLista, RacunPravnogLica) {
        var vm = this;

        vm.banka = entity;
        vm.clear = clear;
        vm.save = save;
        vm.kursnalistas = KursnaLista.query();
        vm.racunpravnoglicas = RacunPravnogLica.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.banka.id !== null) {
                Banka.update(vm.banka, onSaveSuccess, onSaveError);
            } else {
                Banka.save(vm.banka, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pinfProApp:bankaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
