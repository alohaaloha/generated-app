(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaController', DrzavaController);

    DrzavaController.$inject = ['$scope', '$state', 'Drzava'];

    function DrzavaController ($scope, $state, Drzava) {
        var vm = this;
        vm.drzavas = [];
        vm.loadAll = function() {
            Drzava.query(function(result) {
                vm.drzavas = result;
            });
        };

        vm.loadAll();


        $scope.gotoNaseljenaMesta=function(drzava){
            $state.go("naseljeno-mesto",{"drzava":drzava});
        }

        $scope.gotoValute=function(drzava){
            $state.go("valuta",{"drzava":drzava});
        }



    }
})();
