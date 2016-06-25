(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KlijentDialogController', KlijentDialogController);

    KlijentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Klijent', 'RacunPravnogLica', 'PravnoLice', 'NaseljenoMesto', '$rootScope', '$state'];

    function KlijentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Klijent, RacunPravnogLica, PravnoLice, NaseljenoMesto, $rootScope, $state) {
        var vm = this;

        $scope.izbor = function(){
            $uibModalInstance.dismiss('cancel');
            $state.go("naseljeno-mesto",{"drzava":null, "isZoom":true});
        }

        if($rootScope.klijent){
        /*VRACAM SE NA OVO STANJE - ZOOM MEHANIZAM*/
        //ucitas ono sto je sacuvano kad si otisao i popuni polja kako si ostavio
            vm.klijent = $rootScope.klijent;
        }else{
        /*AKO DOLAZIS OVDE SA NEW ILI EDIT */
        //setujes na ono sto se edituje, ili na ono sto ces tek uneti (empty)
                $rootScope.klijent=entity;
                vm.klijent = entity;
                vm.racunpravnoglicas = RacunPravnogLica.query();
                vm.pravnolice = PravnoLice.query();
                vm.naseljenomestos = NaseljenoMesto.query();
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:klijentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            if(vm.klijent.naseljenoMesto){ //naseljeno mesto ne sme biti null
                $rootScope.klijent=null;
                vm.isSaving = true;
                if (vm.klijent.id !== null) {
                    Klijent.update(vm.klijent, onSaveSuccess, onSaveError);
                } else {
                    Klijent.save(vm.klijent, onSaveSuccess, onSaveError);
                }
            }else{
                //TODO - show alertify error here
            }
        };

        vm.clear = function() {
            $rootScope.klijent=null; //pobrise se sa root
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
