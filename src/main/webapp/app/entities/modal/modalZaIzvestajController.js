(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ModalZaIzvestajController', ModalZaIzvestajController);

    ModalZaIzvestajController.$inject = ['$scope', '$state', 'RacunPravnogLica','$rootScope', '$window', '$uibModalInstance', 'param', '$http'];

    function ModalZaIzvestajController ($scope, $state, RacunPravnogLica,$rootScope,$window, $uibModalInstance, param, $http) {
        var vm = this;

        $scope.dataForIzvestaj={};
        $scope.dataForIzvestaj.datum1=null;
        $scope.dataForIzvestaj.datum2=null;
        $scope.dataForIzvestaj.racun =param.data;//"lalala";

        $scope.zatvori=function(){
            $uibModalInstance.dismiss('cancel');
        }

        $scope.zemozemo=function(){
            console.log($scope.dataForIzvestaj);
            var resourceUrl = 'api/generisiizvestaj';
            $http({
                method: 'GET',
                params: { racun : $scope.dataForIzvestaj.racun,
                            datum1: $scope.dataForIzvestaj.datum1,
                            datum2: $scope.dataForIzvestaj.datum2},
                url: resourceUrl
            }).then(function successCallback(response) {
                //alert("Uspresno generisan HTML izvoda.");
                $scope.zatvori();
                $state.go('racun-izvod', {brojRacuna : $scope.dataForIzvestaj.racun });
            }, function errorCallback(response) {
                alert("Doslo je do greske. Izvod nije generisan.");
            });
        }


        $scope.datePickerOpenStatus = {};
        $scope.datePickerOpenStatus.datum = false;
        $scope.datePickerOpenStatus.datum2 = false;
        $scope.openCalendar = function(date) {
            $scope.datePickerOpenStatus[date] = true;
        };


    }
})();
