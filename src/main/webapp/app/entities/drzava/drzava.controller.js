(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaController', DrzavaController);

    DrzavaController.$inject = ['$scope', '$state', 'Drzava', '$stateParams', '$rootScope', '$window'];

    function DrzavaController ($scope, $state, Drzava, $stateParams, $rootScope, $window) {


        //ZOOM STUFF start-------------------------//
        /*0*/
        $scope.isZoom=$rootScope.drzavaZOOM;

        $scope.pick=function(drzava){
            console.log(drzava);
            /*1*/
            $rootScope.naseljenoMesto.drzava=drzava; //ladno mu treba ceo obj a ne samo id
            /*2*/
            $state.drzavaZOOM=false;
            /*3*/
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
