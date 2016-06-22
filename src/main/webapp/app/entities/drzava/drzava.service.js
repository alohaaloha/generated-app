(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('Drzava', Drzava);

    Drzava.$inject = ['$resource'];

    function Drzava ($resource) {
        var resourceUrl =  'api/drzavas/:id';

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
