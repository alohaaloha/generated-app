(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('Valuta', Valuta);

    Valuta.$inject = ['$resource'];

    function Valuta ($resource) {
        var resourceUrl =  'api/valutas/:id';

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
