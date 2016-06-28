(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaDialogController', RacunPravnogLicaDialogController);

    RacunPravnogLicaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RacunPravnogLica', 'Banka', 'Klijent', 'DnevnoStanjeRacuna', 'Valuta', 'Ukidanje','$rootScope','$state','$window'];

    function RacunPravnogLicaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RacunPravnogLica, Banka, Klijent, DnevnoStanjeRacuna, Valuta, Ukidanje,$rootScope,$state,$window) {
        var vm = this;


        $scope.izborBanke = function(){
                        $uibModalInstance.dismiss('cancel');
                        $rootScope.bankaZOOM=true;
                        $state.go("banka");
                }

        if($rootScope.racunPravnogLica){
                        /*Uslov prolazi samo u slucaju da postoji nesto sto je setovano na narednoj formi koristeci ZOOM mehanizam*/
                        //ukoliko postoje parametri koji su setovani u okviru ZOOM mehanizma na narednoj formi, preuzmi ih
                            vm.racunPravnogLica = $rootScope.racunPravnogLica;
                        }else{
                        /*AKO DOLAZIS OVDE SA NEW ILI EDIT */
                        //setujes na ono sto se edituje, ili na ono sto ces tek uneti (empty)
                              $rootScope.racunPravnogLica=entity;
                              vm.racunPravnogLica = entity;
                              vm.bankas = Banka.query();
                              vm.klijents = Klijent.query();
                              vm.dnevnostanjeracunas = DnevnoStanjeRacuna.query();
                              vm.valutas = Valuta.query();
                              vm.ukidanjes = Ukidanje.query();

                        }

        $scope.izborVlasnika = function(){
                $uibModalInstance.dismiss('cancel');
                $rootScope.klijentZOOM=true;
                $state.go("klijent");
        }

        $scope.izborValute = function(){
                $uibModalInstance.dismiss('cancel');
                $rootScope.valutaZOOM=true;
                $state.go("valuta");
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pinfProApp:racunPravnogLicaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            if(vm.racunPravnogLica.valuta && vm.racunPravnogLica.banka && vm.racunPravnogLica.vlasnik){
                $rootScope.racunPravnogLica=null;
                vm.isSaving = true;
                            if (vm.racunPravnogLica.id !== null) {
                                RacunPravnogLica.update(vm.racunPravnogLica, onSaveSuccess, onSaveError);
                            } else {
                                RacunPravnogLica.save(vm.racunPravnogLica, onSaveSuccess, onSaveError);
                            }
            }else{
                //TODO - show alertify error here
            }
            $window.history.back();
        };

        vm.clear = function() {
            $rootScope.racunPravnogLica=null;
            $uibModalInstance.dismiss('cancel');
            //$window.history.back();
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.datumOtvaranja = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
