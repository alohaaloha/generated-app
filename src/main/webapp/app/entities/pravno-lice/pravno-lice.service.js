(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('PravnoLice', PravnoLice);

    PravnoLice.$inject = ['$resource'];

    function PravnoLice ($resource) {
        var resourceUrl =  'api/pravno-lice/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
