(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaController', DrzavaController);

    DrzavaController.$inject = ['$scope', '$state', 'Drzava', '$stateParams', '$rootScope', '$window'];

    function DrzavaController ($scope, $state, Drzava, $stateParams, $rootScope, $window) {


        //ZOOM STUFF start-------------------------//
        /*0*/
        if($rootScope.drzavaZOOM)
        $scope.isZoom=$rootScope.drzavaZOOM;



        $scope.pick=function(drzava){
            console.log(drzava);
            /*1*/
            if($rootScope.naseljenoMesto!==null){
                $rootScope.naseljenoMesto.drzava=drzava; //ladno mu treba ceo obj a ne samo id
                $rootScope.drzavaZOOM=false;
                $state.go("naseljeno-mesto");
            }
            if($rootScope.valuta!==null){
                $rootScope.valuta.drzava=drzava; //ladno mu treba ceo obj a ne samo id
                $rootScope.drzavaZOOM=false;
                $state.go("valuta.new");
            }
            /*2*/

            /*3*/
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

    }
})();
