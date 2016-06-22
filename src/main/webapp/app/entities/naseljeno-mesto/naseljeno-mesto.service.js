(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('NaseljenoMesto', NaseljenoMesto);

    NaseljenoMesto.$inject = ['$resource'];

    function NaseljenoMesto ($resource) {
        var resourceUrl =  'api/naseljeno-mestos/:id';

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
