(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .service('naseljenoMestoService', function($http){
        return{
            getByDrzavaId: function(id, onSuccess, onError){

            var req = {
                method: 'GET',
                url: '/api/naseljeno-mestos-by-drzava-id/'+id,
            }

            $http(req).then(onSuccess, onError);

            }
        }

    });


})();
