(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RTGSDetailController', RTGSDetailController);

    RTGSDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'RTGS', 'AnalitikaIzvoda','$http'];

    function RTGSDetailController($scope, $rootScope, $stateParams, entity, RTGS, AnalitikaIzvoda,$http) {
        var vm = this;
        vm.rTGS = entity;

        var unsubscribe = $rootScope.$on('pinfProApp:rTGSUpdate', function(event, result) {
            vm.rTGS = result;
        });
        $scope.$on('$destroy', unsubscribe);





    $scope.generateXml = function(id){
            var resourceUrl = 'api/generatexml/'+id;
                    $http({
                            method: 'GET',
                            url: resourceUrl
                        }).then(function successCallback(response) {
//                            $state.go('banka-izvod', {sifraBanke : sifraBanke });
                            //alert("USPELI SMO");
                        }, function errorCallback(response) {
                            alert("Doslo je do greske. Izvod banke nije generisan.");
                        });
    }
    }
})();
