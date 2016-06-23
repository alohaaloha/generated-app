(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('RTGS', RTGS);

    RTGS.$inject = ['$resource'];

    function RTGS ($resource) {
        var resourceUrl =  'api/r-tgs/:id';

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
