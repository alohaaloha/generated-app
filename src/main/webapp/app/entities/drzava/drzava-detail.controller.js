(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('DrzavaDetailController', DrzavaDetailController);

    DrzavaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Drzava', 'NaseljenoMesto'];

    function DrzavaDetailController($scope, $rootScope, $stateParams, entity, Drzava, NaseljenoMesto) {
        var vm = this;
        vm.drzava = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:drzavaUpdate', function(event, result) {
            vm.drzava = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
