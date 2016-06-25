(function() {
    'use strict';

    angular
        .module('pinfProApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ENV', 'LoginService', '$scope'];

    function NavbarController ($state, Auth, Principal, ENV, LoginService, $scope) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.inProduction = ENV === 'prod';
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login () {
            collapseNavbar();
            LoginService.open();
        }

        function logout () {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar () {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar () {
            vm.isNavbarCollapsed = true;
        }


  //-------------------file----------------------//

       /* $scope.uploadFile=function(){

        var formData=new FormData();
            formData.append("file",file.files[0]);
            $http.post('/serverApp/rest/newDocument', formData, {
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                },
                headers: { 'Content-Type': undefined }
                }).success(function(data, status) {
                    alert("Success ... " + status);
                }).error(function(data, status) {
                    alert("Error ... " + status);
                    });
        }



         $scope.setFiles = function(element) {
            $scope.$apply(function($scope) {
              console.log('files:', element.files);
              // Turn the FileList object into an Array
                $scope.files = []
                for (var i = 0; i < element.files.length; i++) {
                  $scope.files.push(element.files[i])
                }
              $scope.progressVisible = false
              });
            };

            $scope.uploadFile = function() {
                var fd = new FormData()
                for (var i in $scope.files) {
                    fd.append("uploadedFile", $scope.files[i])
                }
                var xhr = new XMLHttpRequest()
                xhr.upload.addEventListener("progress", uploadProgress, false)
                xhr.addEventListener("load", uploadComplete, false)
                xhr.addEventListener("error", uploadFailed, false)
                xhr.addEventListener("abort", uploadCanceled, false)
                xhr.open("POST", "/fileupload")
                scope.progressVisible = true
                xhr.send(fd)
            }




            function uploadProgress(evt) {
                $scope.$apply(function(){
                    if (evt.lengthComputable) {
                        $scope.progress = Math.round(evt.loaded * 100 / evt.total)
                    } else {
                        $scope.progress = 'unable to compute'
                    }
                })
            }

            function uploadComplete(evt) {
                *//* This event is raised when the server send back a response *//*
                alert(evt.target.responseText)
            }

            function uploadFailed(evt) {
                alert("There was an error attempting to upload the file.")
            }

            function uploadCanceled(evt) {
                $scope.$apply(function(){
                    scope.progressVisible = false
                })
                alert("The upload has been canceled by the user or the browser dropped the connection.")
            }

*/





    }
})();
