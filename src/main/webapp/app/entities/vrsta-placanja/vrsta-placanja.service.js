(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('VrstaPlacanja', VrstaPlacanja);

    VrstaPlacanja.$inject = ['$resource'];

    function VrstaPlacanja ($resource) {
        var resourceUrl =  'api/vrsta-placanjas/:id';

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
