(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('VrstaPlacanjaDeleteController',VrstaPlacanjaDeleteController);

    VrstaPlacanjaDeleteController.$inject = ['$uibModalInstance', 'entity', 'VrstaPlacanja'];

    function VrstaPlacanjaDeleteController($uibModalInstance, entity, VrstaPlacanja) {
        var vm = this;
        vm.vrstaPlacanja = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            VrstaPlacanja.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
