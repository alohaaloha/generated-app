(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('KursUValuti', KursUValuti);

    KursUValuti.$inject = ['$resource'];

    function KursUValuti ($resource) {
        var resourceUrl =  'api/kurs-u-valutis/:id';

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
