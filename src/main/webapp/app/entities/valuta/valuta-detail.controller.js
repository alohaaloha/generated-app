(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('ValutaDetailController', ValutaDetailController);

    ValutaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Valuta', 'Drzava', 'Kliring', 'KursUValuti', 'RacunPravnogLica', 'AnalitikaIzvoda'];

    function ValutaDetailController($scope, $rootScope, $stateParams, entity, Valuta, Drzava, Kliring, KursUValuti, RacunPravnogLica, AnalitikaIzvoda) {
        var vm = this;
        vm.valuta = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:valutaUpdate', function(event, result) {
            vm.valuta = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
