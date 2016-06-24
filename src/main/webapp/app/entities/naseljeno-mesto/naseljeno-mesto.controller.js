(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoController', NaseljenoMestoController);

    NaseljenoMestoController.$inject = ['$scope', '$state', 'NaseljenoMesto', '$stateParams', 'naseljenoMestoService'];

    function NaseljenoMestoController ($scope, $state, NaseljenoMesto, $stateParams, naseljenoMestoService) {
        var vm = this;

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
