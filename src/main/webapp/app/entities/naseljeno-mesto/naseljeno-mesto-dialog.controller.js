(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoDialogController', NaseljenoMestoDialogController);

    NaseljenoMestoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NaseljenoMesto', 'Drzava', 'AnalitikaIzvoda', '$rootScope', '$state'];

    function NaseljenoMestoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NaseljenoMesto, Drzava, AnalitikaIzvoda, $rootScope, $state) {
        var vm = this;

        /*ZOOM*/
        $scope.izbor=function(){
                 /*1*/
                 $uibModalInstance.dismiss('cancel');
                 /*2*/
                 $rootScope.drzavaZOOM=true; //ovo koristimo
                 /*3*/
                 $state.go("drzava");
        }


        if($rootScope.naseljenoMesto){
        /*VRACAM SE NA OVO STANJE - ZOOM MEHANIZAM*/
        //ucitas ono sto je sacuvano kad si otisao i popuni polja kako si ostavio
            vm.naseljenoMesto = $rootScope.naseljenoMesto;
        }else{
        /*AKO DOLAZIS OVDE SA NEW ILI EDIT */
        //setujes na ono sto se edituje, ili na ono sto ces tek uneti (empty)
               $rootScope.naseljenoMesto=entity;
               vm.naseljenoMesto = entity;
               vm.drzavas = Drzava.query();
               vm.analitikaizvodas = AnalitikaIzvoda.query();
        }


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:naseljenoMestoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            /*drzava ne sme null*/
            if(vm.naseljenoMesto.drzava){
               /*pobrise se rootscope za nm*/
               $rootScope.naseljenoMesto=null;
               vm.isSaving = true;
               if (vm.naseljenoMesto.id !== null) {
                   NaseljenoMesto.update(vm.naseljenoMesto, onSaveSuccess, onSaveError);
               } else {
                   NaseljenoMesto.save(vm.naseljenoMesto, onSaveSuccess, onSaveError);
               }
            }else{
                //TODO - show alertify error here
            }

            /*moram ga bacim na state, saban*/
            //$state.go("naseljeno-mesto");
            $window.history.back();
        };

        vm.clear = function() {
            $rootScope.naseljenoMesto=null; //pobrise se sa root
            $uibModalInstance.dismiss('cancel');
            /*moram ga bacim na state, saban*/
            //$state.go("naseljeno-mesto");
            $window.history.back();
        };


    }
})();
