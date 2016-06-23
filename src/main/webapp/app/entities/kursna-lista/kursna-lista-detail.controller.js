(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursnaListaDetailController', KursnaListaDetailController);

    KursnaListaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'KursnaLista', 'Banka', 'KursUValuti'];

    function KursnaListaDetailController($scope, $rootScope, $stateParams, entity, KursnaLista, Banka, KursUValuti) {
        var vm = this;
        vm.kursnaLista = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:kursnaListaUpdate', function(event, result) {
            vm.kursnaLista = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
