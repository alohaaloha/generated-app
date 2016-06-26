(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('Banka', Banka);

    Banka.$inject = ['$resource'];

    function Banka ($resource) {
        var resourceUrl =  'api/bankas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
