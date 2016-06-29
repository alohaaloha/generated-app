(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Upload', '$timeout', '$http'];

    function HomeController ($scope, Principal, LoginService, $state,  Upload, $timeout, $http) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        $scope.obaviKliring = function(){
            var resourceUrl = 'api/klirings/izvrsi';
            $http({
                method: 'GET',
                url: resourceUrl
            }).then(function successCallback(response) {
                alert("Kliring uspesno obavljen.");
            }, function errorCallback(response) {
                alert("Doslo je do greske. Kliring nije izvrsen.");
            });
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
                            $state.go("analitika-izvoda");
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
