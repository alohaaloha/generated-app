(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ValutaDialogController', ValutaDialogController);

    ValutaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Valuta', 'Drzava', 'Kliring', 'KursUValuti', 'RacunPravnogLica', 'AnalitikaIzvoda','$rootScope', '$state','$window'];

    function ValutaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Valuta, Drzava, Kliring, KursUValuti, RacunPravnogLica, AnalitikaIzvoda,$rootScope,$state,$window) {
        var vm = this;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        $scope.izbor = function(){
                      //alert("AJJSAJDAJSJDAJSDJ");
                      $uibModalInstance.dismiss('cancel');
                      $rootScope.drzavaZOOM=true;
                      $state.go("drzava");
                  }

        if($rootScope.valuta){
                /*Uslov prolazi samo u slucaju da postoji nesto sto je setovano na narednoj formi koristeci ZOOM mehanizam*/
                //ukoliko postoje parametri koji su setovani u okviru ZOOM mehanizma na narednoj formi, preuzmi ih
                    vm.valuta = $rootScope.valuta;
                }else{
                /*AKO DOLAZIS OVDE SA NEW ILI EDIT */
                //setujes na ono sto se edituje, ili na ono sto ces tek uneti (empty)
                      $rootScope.valuta=entity;
                      vm.valuta = entity;
                      vm.drzavas = Drzava.query();
                      vm.klirings = Kliring.query();
                      vm.kursuvalutis = KursUValuti.query();
                      vm.racunpravnoglicas = RacunPravnogLica.query();
                      vm.analitikaizvodas = AnalitikaIzvoda.query();

                }

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:valutaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
             if(vm.valuta.drzava){ //drzava ne sme biti null
                $rootScope.valuta=null;
                vm.isSaving = true;
                    if (vm.valuta.id !== null) {
                        Valuta.update(vm.valuta, onSaveSuccess, onSaveError);
                    } else {
                        Valuta.save(vm.valuta, onSaveSuccess, onSaveError);
                    }
            }else{
                //TODO - show alertify error here
            }
             $window.history.back();
        };

        vm.clear = function() {
            $rootScope.valuta=null;
            $uibModalInstance.dismiss('cancel');
            $window.history.back();
        };
    }
})();
