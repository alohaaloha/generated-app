(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('AnalitikaIzvodaController', AnalitikaIzvodaController);

    AnalitikaIzvodaController.$inject = ['$scope', '$state', 'AnalitikaIzvoda', 'Upload', '$timeout'];

    function AnalitikaIzvodaController ($scope, $state, AnalitikaIzvoda, Upload, $timeout) {
        var vm = this;
        vm.analitikaIzvodas = [];
        vm.loadAll = function() {
            AnalitikaIzvoda.query(function(result) {
                vm.analitikaIzvodas = result;
            });
        };

        vm.loadAll();


        /*$scope.reloadRoute = function() {
           $route.reload();
        }

        /* file upload - sve samo radi, da nema angulara riko bi*/

        $scope.uploadFiles = function(file, errFiles) {
                $scope.f = file;
                $scope.errFile = errFiles && errFiles[0];
                if (file) {
                    file.upload = Upload.upload({
                        url: '/api/upload',
                        data: {file: file}
                    });

                    file.upload.then(function (response) {
                        $timeout(function () {
                            file.result = response.data;
                            $state.reload();
                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = response.status + ': ' + response.data;
                    }, function (evt) {
                        file.progress = Math.min(100, parseInt(100.0 *
                                                 evt.loaded / evt.total));
                    });
                }
            }
    }
})();
