(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoDialogController', NaseljenoMestoDialogController);

    NaseljenoMestoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NaseljenoMesto', 'Drzava', 'AnalitikaIzvoda', '$rootScope', '$state'];

    function NaseljenoMestoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NaseljenoMesto, Drzava, AnalitikaIzvoda, $rootScope, $state) {
        var vm = this;

        $scope.test=function(){
                //alert("aaaaaaaaaaa");
                 $uibModalInstance.dismiss('cancel');
                 $state.go("drzava",{"isZoom":true});

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
               if(vm.naseljenoMesto.drzava){ //drzava ne sme null
                   $rootScope.naseljenoMesto=null; //pobrise se sa root
                   vm.isSaving = true;
                   if (vm.naseljenoMesto.id !== null) {
                       NaseljenoMesto.update(vm.naseljenoMesto, onSaveSuccess, onSaveError);
                   } else {
                       NaseljenoMesto.save(vm.naseljenoMesto, onSaveSuccess, onSaveError);
                   }
               }else{
                    //TODO - show alertify error here
               }
           };

           vm.clear = function() {
               $rootScope.naseljenoMesto=null; //pobrise se sa root
               $uibModalInstance.dismiss('cancel');
           };


    }
})();
