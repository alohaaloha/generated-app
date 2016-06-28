(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ValutaController', ValutaController);

    ValutaController.$inject = ['$scope', '$state', 'Valuta','$rootScope'];

    function ValutaController ($scope, $state, Valuta,$rootScope) {
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

        vm.valutas = [];
        vm.loadAll = function() {
            Valuta.query(function(result) {
                vm.valutas = result;
            });
        };

        vm.loadAll();

    }
})();
