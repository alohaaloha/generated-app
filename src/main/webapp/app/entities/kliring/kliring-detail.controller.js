(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KliringDetailController', KliringDetailController);

    KliringDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Kliring', 'StavkaKliringa', 'Valuta','$http'];

    function KliringDetailController($scope, $rootScope, $stateParams, entity, Kliring, StavkaKliringa, Valuta,$http) {
        var vm = this;

        vm.kliring = entity;

        var unsubscribe = $rootScope.$on('pinfProApp:kliringUpdate', function(event, result) {
            vm.kliring = result;
        });
        $scope.$on('$destroy', unsubscribe);

         $scope.generateXml = function(id){
                    var resourceUrl = 'api/clearingxml/'+id;
                            $http({
                                    method: 'GET',
                                    url: resourceUrl
                                }).then(function successCallback(response) {
        //                            $state.go('banka-izvod', {sifraBanke : sifraBanke });
                                    //alert("USPELI SMO");
                                }, function errorCallback(response) {
                                    alert("Doslo je do greske. Izvod kliringa nije generisan.");
                                });
            }

    }
})();
