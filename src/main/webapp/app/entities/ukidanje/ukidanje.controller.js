(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('UkidanjeController', UkidanjeController);

    UkidanjeController.$inject = ['$scope', '$state', 'Ukidanje', '$stateParams'];

    function UkidanjeController ($scope, $state, Ukidanje, $stateParams) {
        var vm = this;
        vm.ukidanjes = [];
        vm.loadAll = function() {
            Ukidanje.query(function(result) {
                vm.ukidanjes = result;
            });
        };

        /*UKIDANJA RACUNA ZA KONKRETNO JEDAN RACUN PRAVNOG LICA*/
        if($stateParams.racunPravnogLica){

            $scope.racunPravnogLica=$stateParams.racunPravnogLica;
            console.log("TRAZIM UKIDANJA ZA RACUN");
            console.log($stateParams.racunPravnogLica.brojRacuna);
            vm.ukidanjes = $scope.racunPravnogLica.ukidanjes;

        }else{
            /*AKO SI DOSAO DA VIDIS SVA DNEVNA STANJA*/
            console.log("TRAZIM SVA UKIDANJA ");
            vm.loadAll();
        }

    }
})();
