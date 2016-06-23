(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('StavkaKliringa', StavkaKliringa);

    StavkaKliringa.$inject = ['$resource'];

    function StavkaKliringa ($resource) {
        var resourceUrl =  'api/stavka-kliringas/:id';

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
