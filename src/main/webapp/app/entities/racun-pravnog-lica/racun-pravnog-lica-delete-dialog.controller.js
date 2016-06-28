(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('RacunPravnogLicaDeleteController',RacunPravnogLicaDeleteController);

    RacunPravnogLicaDeleteController.$inject = ['$uibModalInstance', 'entity', 'RacunPravnogLica', '$http', 'Ukidanje'];

    function RacunPravnogLicaDeleteController($uibModalInstance, entity, RacunPravnogLica, $http, Ukidanje) {
        var vm = this;
        vm.racunPravnogLica = entity;
        vm.ukidanje = {};
        vm.ukidanje.racunPravnogLica = entity;
        vm.ukidanje.id = null;
        vm.ukidanje.datumUkidanja = null;
        vm.ukidanje.prenosNaRacun = null;
        vm.id = null;

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (entitetUkidanje) {
            var req = {
             method: 'POST',
             url: 'http://localhost:8080/api/ukidanjes',
             data: { ukidanje: entitetUkidanje }
            }
            entitetUkidanje.datumUkidanja = new Date();
            Ukidanje.save(entitetUkidanje, function(){$uibModalInstance.close(true);}, function(){$uibModalInstance.close(false);});
            //$http(req).then(function(){$uibModalInstance.close(true);}, function(){$uibModalInstance.close(false);});
            //RacunPravnogLica.delete({id: id},
            //    function () {
            //        $uibModalInstance.close(true);
            //    });
        };
    }
})();
