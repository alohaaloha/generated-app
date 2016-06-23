(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KlijentDetailController', KlijentDetailController);

    KlijentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Klijent', 'RacunPravnogLica', 'PravnoLice', 'NaseljenoMesto'];

    function KlijentDetailController($scope, $rootScope, $stateParams, entity, Klijent, RacunPravnogLica, PravnoLice, NaseljenoMesto) {
        var vm = this;
        vm.klijent = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:klijentUpdate', function(event, result) {
            vm.klijent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
