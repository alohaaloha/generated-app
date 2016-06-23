(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('UkidanjeDetailController', UkidanjeDetailController);

    UkidanjeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Ukidanje', 'RacunPravnogLica'];

    function UkidanjeDetailController($scope, $rootScope, $stateParams, entity, Ukidanje, RacunPravnogLica) {
        var vm = this;
        vm.ukidanje = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:ukidanjeUpdate', function(event, result) {
            vm.ukidanje = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
