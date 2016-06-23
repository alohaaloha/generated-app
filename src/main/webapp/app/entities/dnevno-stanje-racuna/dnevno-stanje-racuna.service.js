(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('DnevnoStanjeRacuna', DnevnoStanjeRacuna);

    DnevnoStanjeRacuna.$inject = ['$resource', 'DateUtils'];

    function DnevnoStanjeRacuna ($resource, DateUtils) {
        var resourceUrl =  'api/dnevno-stanje-racunas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datum = DateUtils.convertDateTimeFromServer(data.datum);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
