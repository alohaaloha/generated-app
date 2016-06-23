(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaDetailController', DrzavaDetailController);

    DrzavaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Drzava', 'NaseljenoMesto', 'Valuta'];

    function DrzavaDetailController($scope, $rootScope, $stateParams, entity, Drzava, NaseljenoMesto, Valuta) {
        var vm = this;
        vm.drzava = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:drzavaUpdate', function(event, result) {
            vm.drzava = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
