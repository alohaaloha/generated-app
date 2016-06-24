(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaController', DrzavaController);

    DrzavaController.$inject = ['$scope', '$state', 'Drzava', '$stateParams', '$rootScope'];

    function DrzavaController ($scope, $state, Drzava, $stateParams, $rootScope) {


        //ZOOM STUFF start-------------------------//
        $scope.isZoom=$stateParams.isZoom;

        $scope.pick=function(drzava){
            console.log(drzava);
            $rootScope.naseljenoMesto.drzava=drzava; //ladno mu treba ceo obj a ne samo id
            $state.go("naseljeno-mesto.new");

        }
        //ZOOM STUFF end---------------------------//


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
