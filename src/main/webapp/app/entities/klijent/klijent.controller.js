(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KlijentController', KlijentController);

    KlijentController.$inject = ['$scope', '$state', 'Klijent','$rootScope'];

    function KlijentController ($scope, $state, Klijent,$rootScope) {
        var vm = this;

        if($rootScope.klijentZOOM)
                   $scope.isZoom=$rootScope.klijentZOOM;

        $scope.pick=function(klijent){
                             console.log(klijent);
                             /*1*/

                             if($rootScope.racunPravnogLica!==null){
                                 $rootScope.racunPravnogLica.vlasnik=klijent; //ladno mu treba ceo obj a ne samo id
                                 $rootScope.klijentZOOM=false;
                                 $state.go("racun-pravnog-lica.new");
                             }
                             /*2*/

                             /*3*/
                         }


        vm.klijents = [];
        vm.loadAll = function() {
            Klijent.query(function(result) {
                vm.klijents = result;
            });
        };

        vm.loadAll();

    }
})();
