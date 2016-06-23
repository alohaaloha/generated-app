(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('Klijent', Klijent);

    Klijent.$inject = ['$resource'];

    function Klijent ($resource) {
        var resourceUrl =  'api/klijents/:id';

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
