(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ModalZaIzvestajController', ModalZaIzvestajController);

    ModalZaIzvestajController.$inject = ['$scope', '$state', 'RacunPravnogLica','$rootScope', '$window', '$uibModalInstance', 'param'];

    function ModalZaIzvestajController ($scope, $state, RacunPravnogLica,$rootScope,$window, $uibModalInstance, param) {
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
            //TODO - call rest and send it '$scope.dataForIzvestaj' object

         }


        $scope.datePickerOpenStatus = {};
        $scope.datePickerOpenStatus.datum = false;
        $scope.datePickerOpenStatus.datum2 = false;
        $scope.openCalendar = function(date) {
            $scope.datePickerOpenStatus[date] = true;
        };


    }
})();
