(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('BankaController', BankaController);

    BankaController.$inject = ['$scope', '$state', 'Banka','$rootScope'];

    function BankaController ($scope, $state, Banka,$rootScope) {
        var vm = this;

        if($rootScope.bankaZOOM)
           $scope.isZoom=$rootScope.bankaZOOM;

        $scope.pick=function(banka){
                             console.log(banka);
                             /*1*/

                             if($rootScope.racunPravnogLica!==null){
                                 $rootScope.racunPravnogLica.banka=banka; //ladno mu treba ceo obj a ne samo id
                                 $rootScope.bankaZOOM=false;
                                 $state.go("racun-pravnog-lica.new");
                             }
                             /*2*/

                             /*3*/
                         }


        vm.bankas = [];

        loadAll();

        function loadAll() {
            Banka.query(function(result) {
                vm.bankas = result;
            });
        }
    }
})();
