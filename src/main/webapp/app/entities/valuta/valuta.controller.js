(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ValutaController', ValutaController);

    ValutaController.$inject = ['$scope', '$state', 'Valuta','$rootScope','$stateParams'];

    function ValutaController ($scope, $state, Valuta,$rootScope,$stateParams) {
        var vm = this;

         if($rootScope.valutaZOOM)
                           $scope.isZoom=$rootScope.valutaZOOM;


         $scope.pick=function(valuta){
                     console.log(valuta);
                     /*1*/

                     if($rootScope.racunPravnogLica!==null){
                         $rootScope.racunPravnogLica.valuta=valuta; //ladno mu treba ceo obj a ne samo id
                         $rootScope.valutaZOOM=false;
                         $state.go("racun-pravnog-lica.new");
                     }
                     /*2*/

                     /*3*/
                 }



        if($stateParams.drzava){

                    $scope.drzava=$stateParams.drzava;
//                    console.log("TRAZIM VALUTE ZA DRZAVU");
//                    console.log($stateParams.drzava);
                    vm.valutas = $scope.drzava.drzavnaValutas;

                }else{


                 console.log("TRAZIM SVE VALUTE.");
                  vm.valutas = [];
                         vm.loadAll = function() {
                             Valuta.query(function(result) {
                                 vm.valutas = result;
                             });
                         };

                         vm.loadAll();

                }

    }
})();
