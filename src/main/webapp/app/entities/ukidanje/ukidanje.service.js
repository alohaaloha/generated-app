(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('Ukidanje', Ukidanje);

    Ukidanje.$inject = ['$resource', 'DateUtils'];

    function Ukidanje ($resource, DateUtils) {
        var resourceUrl =  'api/ukidanjes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datumUkidanja = DateUtils.convertDateTimeFromServer(data.datumUkidanja);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
