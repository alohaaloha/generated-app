(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('BankaDetailController', BankaDetailController);

    BankaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Banka', 'KursnaLista', 'RacunPravnogLica', '$http', '$state'];

    function BankaDetailController($scope, $rootScope, $stateParams, entity, Banka, KursnaLista, RacunPravnogLica, $http, $state) {
        var vm = this;

        vm.banka = entity;

        var unsubscribe = $rootScope.$on('pinfProApp:bankaUpdate', function(event, result) {
            vm.banka = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.generisiIzvodBanke = function(sifraBanke){console.log($scope.dataForIzvestaj);
        var resourceUrl = 'api/generisibankaizvod';
        $http({
                method: 'GET',
                params: { sifraBanke : sifraBanke },
                url: resourceUrl
            }).then(function successCallback(response) {
                $state.go('banka-izvod', {sifraBanke : sifraBanke });
            }, function errorCallback(response) {
                alert("Doslo je do greske. Izvod banke nije generisan.");
            });

        }
    }
})();
