(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NaseljenoMestoDetailController', NaseljenoMestoDetailController);

    NaseljenoMestoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'NaseljenoMesto', 'Drzava'];

    function NaseljenoMestoDetailController($scope, $rootScope, $stateParams, entity, NaseljenoMesto, Drzava) {
        var vm = this;
        vm.naseljenoMesto = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:naseljenoMestoUpdate', function(event, result) {
            vm.naseljenoMesto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
