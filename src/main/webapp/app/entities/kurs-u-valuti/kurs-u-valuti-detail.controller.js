(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('KursUValutiDetailController', KursUValutiDetailController);

    KursUValutiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'KursUValuti', 'Valuta', 'KursnaLista'];

    function KursUValutiDetailController($scope, $rootScope, $stateParams, entity, KursUValuti, Valuta, KursnaLista) {
        var vm = this;
        vm.kursUValuti = entity;
        
        var unsubscribe = $rootScope.$on('pinfProApp:kursUValutiUpdate', function(event, result) {
            vm.kursUValuti = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
