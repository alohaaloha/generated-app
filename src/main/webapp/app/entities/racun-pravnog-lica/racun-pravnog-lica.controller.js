(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaController', RacunPravnogLicaController);

    RacunPravnogLicaController.$inject = ['$scope', '$state', 'RacunPravnogLica','$rootScope', '$window', '$uibModal', '$http', '$stateParams'];

    function RacunPravnogLicaController ($scope, $state, RacunPravnogLica,$rootScope,$window, $uibModal, $http, $stateParams) {
        var vm = this;



        if($stateParams.klijent){

            console.log("ZOOMARA MAMARA");
            console.log($stateParams.klijent);
            vm.racunPravnogLicas = $stateParams.klijent.racunis;


        }else{

            console.log("REGULAR");
             vm.racunPravnogLicas = [];
                vm.loadAll = function() {
                    RacunPravnogLica.query(function(result) {
                        vm.racunPravnogLicas = result;
                    });
                };

                vm.loadAll();


        }








        //MODAL
        $scope.openDialogForIzvestajDatePicker=function(brojRacuna){

             var modalInstance = $uibModal.open({
                    templateUrl: 'app/entities/modal/modalZaIzvestaj.html',
                    controller: 'ModalZaIzvestajController',
                    resolve: {
                                       param: function () {
                                           console.log("SALJEM MODALNOME SABANU: "+brojRacuna);
                                           return {'data':brojRacuna};
                                       }
                                   }
                });

        }


    $scope.izvestajMitric=function(brojRacuna){

            var resourceUrl = 'api/generisiizvestajmitric/'+brojRacuna;
            $http({
                method: 'GET',
                url: resourceUrl
            }).then(function successCallback(response) {
                //alert("Sve kul, radi!");
                window.open("/api/dwn1");
            }, function errorCallback(response) {
                alert("Doslo je do greske. Izvod nije generisan.");
            });

    }






    }
})();
