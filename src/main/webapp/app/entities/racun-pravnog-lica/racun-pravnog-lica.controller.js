(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaController', RacunPravnogLicaController);

    RacunPravnogLicaController.$inject = ['$scope', '$state', 'RacunPravnogLica','$rootScope', '$window', '$uibModal'];

    function RacunPravnogLicaController ($scope, $state, RacunPravnogLica,$rootScope,$window, $uibModal) {
        var vm = this;

        vm.racunPravnogLicas = [];
        vm.loadAll = function() {
            RacunPravnogLica.query(function(result) {
                vm.racunPravnogLicas = result;
            });
        };

        vm.loadAll();




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







    }
})();
