(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('BankaDetailController', BankaDetailController);

    BankaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Banka', 'KursnaLista', 'RacunPravnogLica', '$http', '$state', '$window'];

    function BankaDetailController($scope, $rootScope, $stateParams, entity, Banka, KursnaLista, RacunPravnogLica, $http, $state, $window) {
        var vm = this;
        vm.banka = entity;

        var unsubscribe = $rootScope.$on('pinfProApp:bankaUpdate', function(event, result) {
            vm.banka = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.generisiIzvodBanke = function(sifraBanke){console.log($scope.dataForIzvestaj);
        var resourceUrl = 'api/generisibankaizvod';
        var newWindow = $window.open('app/entities/modal/Loading.html');
        $http({
                method: 'GET',
                params: { sifraBanke : sifraBanke },
                url: resourceUrl
            }).then(function successCallback(response) {
                //$state.go('banka-izvod', {sifraBanke : sifraBanke });
                //$window.location.href = ('app/entities/banka/JasperReport2.html');
                newWindow.location = 'app/entities/banka/JasperReport2.html';
            }, function errorCallback(response) {
                alert("Doslo je do greske. Izvod banke nije generisan.");
            });

        }
    }
})();
