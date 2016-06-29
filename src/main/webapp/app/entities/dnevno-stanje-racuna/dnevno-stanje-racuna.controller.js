(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DnevnoStanjeRacunaController', DnevnoStanjeRacunaController);

    DnevnoStanjeRacunaController.$inject = ['$scope', '$state', 'DnevnoStanjeRacuna', '$stateParams'];

    function DnevnoStanjeRacunaController ($scope, $state, DnevnoStanjeRacuna, $stateParams) {
        var vm = this;
        vm.dnevnoStanjeRacunas = [];
        vm.loadAll = function() {
            DnevnoStanjeRacuna.query(function(result) {
                vm.dnevnoStanjeRacunas = result;
            });
        };

        /*DNEVNA STANJA RACUNA ZA KONKRETNO JEDAN RACUN PRAVNOG LICA*/
        if($stateParams.racunPravnogLica){

            $scope.racunPravnogLica=$stateParams.racunPravnogLica;
            console.log("TRAZIM DNEVNA STANJA ZA RACUN");
            console.log($stateParams.racunPravnogLica.brojRacuna);
            vm.dnevnoStanjeRacunas = $scope.racunPravnogLica.dnevnoStanjeRacunas;

        }else{
            /*AKO SI DOSAO DA VIDIS SVA DNEVNA STANJA*/
            console.log("TRAZIM SVA DNEVNA STANJA ");
            vm.loadAll();
        }
    }
})();
