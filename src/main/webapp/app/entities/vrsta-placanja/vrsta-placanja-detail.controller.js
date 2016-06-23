(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('VrstaPlacanjaDetailController', VrstaPlacanjaDetailController);

    VrstaPlacanjaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'VrstaPlacanja', 'AnalitikaIzvoda'];

    function VrstaPlacanjaDetailController($scope, $rootScope, $stateParams, entity, VrstaPlacanja, AnalitikaIzvoda) {
        var vm = this;
        vm.vrstaPlacanja = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:vrstaPlacanjaUpdate', function(event, result) {
            vm.vrstaPlacanja = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
