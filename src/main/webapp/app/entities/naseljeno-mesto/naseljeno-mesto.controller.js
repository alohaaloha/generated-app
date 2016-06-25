(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoController', NaseljenoMestoController);

    NaseljenoMestoController.$inject = ['$scope', '$state', 'NaseljenoMesto', '$stateParams', 'naseljenoMestoService', '$rootScope'];

    function NaseljenoMestoController ($scope, $state, NaseljenoMesto, $stateParams, naseljenoMestoService, $rootScope) {
        var vm = this;

        //ZOOM STUFF start-------------------------//
        $scope.isZoom = $stateParams.isZoom;
        $scope.pick = function(naseljenoMesto){
            console.log(naseljenoMesto);
            $rootScope.klijent.naseljenoMesto = naseljenoMesto; //ladno mu treba ceo obj a ne samo id
            $state.go("klijent.new");
        }
        //ZOOM STUFF end---------------------------//

        /*NASELJENA MESTA ZA POSLATI ID*/
        if($stateParams.drzava){

            $scope.drzava=$stateParams.drzava;
            console.log("TRAZIM NAS.MESTA ZA DRZAVU");

            naseljenoMestoService.getByDrzavaId(

                $scope.drzava.id,
                function(res){
                console.log("SUCCESS");
                console.log(res.data)
                 vm.naseljenoMestos = res.data;
                },
                function(res){

                }

            );



        }else{

         /*AKO SI DOSAO DA VIDIS STA NASALJENA MESTA*/
         console.log("TRAZIM SVA NAS.MESTA ");
          vm.naseljenoMestos = [];
                    vm.loadAll = function() {
                        NaseljenoMesto.query(function(result) {
                            vm.naseljenoMestos = result;
                        });
                    };

                    vm.loadAll();

        }

    }
})();
